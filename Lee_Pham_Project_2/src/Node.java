class Node<T, U>
{
    U value;
    T key;
    Node pre = null;
    Node next = null;

    /**
     * Sets the key and value of the node
     * @param key
     * @param value
     */
    Node(T key, U value)
    {
        this.value = value;
        this.key=key;
    }
}
