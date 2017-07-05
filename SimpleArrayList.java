import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArrayList<E> implements SimpleList<E> {
    private static final int DEFAUL_INITIAL_CAPACITY = 16;
    private E[] data;
    private int size = 0;

    public SimpleArrayList() {
        this(DEFAUL_INITIAL_CAPACITY);
    }

    public SimpleArrayList(int initialCapacity) {
        this.data = (E[]) new Object[initialCapacity];
    }

    @Override
    public boolean add(E newElement) {
        ensureCapacity(size + 1);

        data[size] = newElement;

        size++;

        return true;
    }

    @Override
    public void add(int index, E element) {
        rangeCheck(index);

        System.arraycopy(data, index, data, index + 1, size - index);

        data[index] = element;

        size++;
    }

    private void rangeCheck(int index) {
        if (index < 0 || size < index) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(minCapacity, data.length + (data.length >> 1));

            E[] newData = (E[]) new Object[newCapacity];

            System.arraycopy(data, 0, newData, 0, data.length);

            this.data = newData;
        }
    }

    @Override
    public E get(int index) {
        rangeCheck(index);

        return data[index];
    }

    @Override
    public boolean contains(Object element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (null == data[i]) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(data[i])) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E set(int index, E newElement) {
        rangeCheck(index);

        E oldElement = data[index];

        data[index] = newElement;

        return oldElement;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        E oldValue = data[index];
        int numMoved = size - index - 1;

        System.arraycopy(data, index + 1, data, index, numMoved);

        data[--size] = null;

        return oldValue;
    }

    @Override
    public boolean remove(Object obj) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(obj)) {
                remove(i);

                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        private int cursor;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }

            return data[cursor++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int index = 0; index < size; index++) {
            sb.append(data[index]);

            if (index == size-1) break;

            sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }
}