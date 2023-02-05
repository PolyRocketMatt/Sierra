package com.github.polyrocketmatt.sierra.lib.buffer;

import com.github.polyrocketmatt.sierra.lib.Parallelizable;
import com.github.polyrocketmatt.sierra.lib.exception.SierraArgumentException;
import com.github.polyrocketmatt.sierra.lib.exception.SierraOperationException;
import com.github.polyrocketmatt.sierra.lib.utils.TriFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public abstract class AsyncBuffer<T> implements Parallelizable, Cloneable {

    private final int width;
    private final int height;
    private final int chunksX;
    private final int chunksZ;
    private final Class<T> clazz;
    private List<BufferChunk<T>> chunks;
    private int bufferThreads = 32;
    private int maxThreads = 32;

    public AsyncBuffer(int width, int height, int chunksX, int chunksZ, Class<T> clazz) throws SierraArgumentException {
        if (width <= 0 || height <= 0)
            throw new SierraArgumentException("Width and height must be greater than 0");
        if (chunksX < 0 || chunksZ < 0)
            throw new SierraArgumentException("Chunk size must be greater than or equal to 0");
        if (width % chunksX != 0 || height % chunksZ != 0)
            throw new SierraArgumentException("Width and height must be divisible by chunk size");

        this.width = width;
        this.height = height;
        this.chunksX = chunksX;
        this.chunksZ = chunksZ;
        this.clazz = clazz;

        this.initialiseBuffer();
    }

    private void initialiseBuffer() {
        this.bufferThreads = Math.min(maxThreads, (width / chunksX) * (height / chunksZ));
        this.chunks = new ArrayList<>();

        for (int x = 0; x < width; x += chunksX)
            for (int z = 0; z < height; z += chunksZ)
                this.chunks.add(new BufferChunk<>(x, z, chunksX, chunksZ, clazz));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getChunksX() {
        return chunksX;
    }

    public int getChunksZ() {
        return chunksZ;
    }

    public BufferChunk<T> getChunk(int x, int z) {
        return chunks.get(x + z * (width / chunksX));
    }

    public T get(int x, int z) {
        return getChunk(x / chunksX, z / chunksZ).get(x % chunksX, z % chunksZ);
    }

    public void set(int x, int z, T value) {
        getChunk(x / chunksX, z / chunksZ).set(x % chunksX, z % chunksZ, value);
    }

    public void map(Function<T, T> map) throws SierraOperationException {
        ExecutorService service = Executors.newFixedThreadPool(bufferThreads);

        for (BufferChunk<T> chunk : chunks) {
            Runnable task = () -> {
                try {
                    for (int x = 0; x < chunk.getChunkWidth(); x++)
                        for (int z = 0; z < chunk.getChunkHeight(); z++)
                            chunk.set(x, z, map.apply(chunk.get(x, z)));
                } catch (Exception ex) { throw new SierraOperationException("Failed to map chunk: %s".formatted(ex.getMessage())); }
            };

            service.execute(task);
        }

        //  Wait for all tasks to complete
        service.shutdown();

        try {
            boolean finished = service.awaitTermination(1, java.util.concurrent.TimeUnit.MINUTES);
            if (!finished)
                throw new SierraOperationException("Failed to complete all tasks");
        } catch (InterruptedException ex) {
            throw new SierraOperationException("Interrupted while waiting for tasks to complete: %s".formatted(ex.getMessage()));
        }
    }

    public void mapIndexed(TriFunction<Integer, Integer, T, T> map) throws SierraOperationException {
        ExecutorService service = Executors.newFixedThreadPool(bufferThreads);

        for (BufferChunk<T> chunk : chunks) {
            int cX = chunk.getX();
            int cZ = chunk.getZ();

            Runnable task = () -> {
                try {
                    for (int x = 0; x < chunk.getChunkWidth(); x++)
                        for (int z = 0; z < chunk.getChunkHeight(); z++)
                            chunk.set(x, z, map.apply(cZ+ x, cX + z, chunk.get(x, z)));
                } catch (Exception ex) { throw new SierraOperationException("Failed to map chunk: %s".formatted(ex.getMessage())); }
            };

            service.execute(task);
        }

        //  Wait for all tasks to complete
        service.shutdown();

        try {
            boolean finished = service.awaitTermination(1, TimeUnit.MINUTES);
            if (!finished)
                throw new SierraOperationException("Failed to complete all tasks");
        } catch (InterruptedException ex) {
            throw new SierraOperationException("Interrupted while waiting for tasks to complete: %s".formatted(ex.getMessage()));
        }
    }

    public void fill(T instance) throws SierraOperationException {
        this.map((T t) -> instance);
    }

    public void flipHorizontal() throws SierraOperationException {
        //  Flip chunks horizontally and re-arrange their positions
        ExecutorService service = Executors.newFixedThreadPool(bufferThreads);
        List<BufferChunk<T>> newChunks = new ArrayList<>();

        for (BufferChunk<T> chunk : chunks) {
            Runnable task = () -> {
                //  Compute new chunk position
                int newX = width - chunk.getX() - chunk.getChunkWidth();
                int newZ = chunk.getZ();

                //  Flip and construct
                BufferChunk<T> newChunk = chunk.to(newX, newZ);
                newChunk.flipHorizontal();

                //  Add to new list
                newChunks.add(newChunk);
            };

            service.submit(task);
        }

        //  Wait for all tasks to complete
        service.shutdown();

        try {
            boolean finished = service.awaitTermination(1, TimeUnit.MINUTES);
            if (!finished)
                throw new SierraOperationException("Failed to complete all tasks");

            if (newChunks.size() != chunks.size())
                throw new SierraOperationException("Failed to flip buffer horizontally");

            //  Set new chunks
            this.chunks = newChunks;
        } catch (InterruptedException ex) {
            throw new SierraOperationException("Interrupted while waiting for tasks to complete: %s".formatted(ex.getMessage()));
        }
    }

    public void flipVertical() throws SierraOperationException {
        //  Flip chunks horizontally and re-arrange their positions
        ExecutorService service = Executors.newFixedThreadPool(bufferThreads);
        List<BufferChunk<T>> newChunks = new ArrayList<>();

        for (BufferChunk<T> chunk : chunks) {
            Runnable task = () -> {
                //  Compute new chunk position
                int newX = width - chunk.getX() - chunk.getChunkWidth();
                int newZ = chunk.getZ();

                //  Flip and construct
                BufferChunk<T> newChunk = chunk.to(newX, newZ);
                newChunk.flipVertical();

                //  Add to new list
                newChunks.add(newChunk);
            };

            service.execute(task);
        }

        //  Wait for all tasks to complete
        service.shutdown();

        try {
            boolean finished = service.awaitTermination(1, TimeUnit.MINUTES);
            if (!finished)
                throw new SierraOperationException("Failed to complete all tasks");

            if (newChunks.size() != chunks.size())
                throw new SierraOperationException("Failed to flip buffer vertically");

            //  Set new chunks
            this.chunks = newChunks;
        } catch (InterruptedException ex) {
            throw new SierraOperationException("Interrupted while waiting for tasks to complete: %s".formatted(ex.getMessage()));
        }
    }

    public abstract void plus(AsyncBuffer<T> buffer) throws SierraOperationException;
    public abstract void minus(AsyncBuffer<T> buffer) throws SierraOperationException;
    public abstract void multiply(AsyncBuffer<T> buffer) throws SierraOperationException;
    public abstract void divide(AsyncBuffer<T> buffer) throws SierraOperationException;

    @Override
    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
        this.bufferThreads = Math.min(maxThreads, (width / chunksX) * (height / chunksZ));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("AsyncBuffer cannot be cloned");
    }
}
