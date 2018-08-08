package org.python.stdlib.collections;

// TODO: support copy.copy(Deque) and copy.deepcopy(Deque) and pickling

public class Deque extends org.python.types.Object {
    static {
        org.python.types.Type.declarePythonType(Deque.class, "collections.deque", null, null);
    }

    @org.python.Attribute
    public org.python.Object maxlen;

    private java.util.LinkedList<org.python.Object> linkedList;

    private Deque() {
        this.maxlen = null;
        this.linkedList = null;
    }

    @org.python.Method(
            __doc__ =
                "deque([iterable[, maxlen]]) --> deque object\n" +
                "\n" +
                "A list-like sequence optimized for data accesses near its endpoints.\n" +
                "\n",
            default_args = {"iterable", "maxlen"}
    )
    public Deque(org.python.Object[] args, java.util.Map<java.lang.String, org.python.Object> kwargs) {
        if (args[1] != null) {
            this.maxlen = args[1];
            if (!(this.maxlen instanceof org.python.types.Int)) {
                throw new org.python.exceptions.TypeError("an integer is required");
            }
        } else {
            this.maxlen = org.python.types.NoneType.NONE;
        }

        this.linkedList = new java.util.LinkedList<org.python.Object>();
        if (args[0] != null) {
            org.python.Object iter = org.Python.iter(args[0]);
            try {
                while (true) {
                    this.append(iter.__next__());
                }
            } catch (org.python.exceptions.StopIteration e) {
            }
        }
    }

    private int _len() {
        return this.linkedList.size();
    }

    @org.python.Method(
            __doc__ = "Return repr(self)."
    )
    public org.python.types.Str __repr__() {
        java.lang.StringBuilder buffer = new java.lang.StringBuilder("[");
        boolean first = true;
        for (org.python.Object obj : this.linkedList) {
            if (first) {
                first = false;
            } else {
                buffer.append(", ");
            }
            buffer.append(obj.__repr__());
        }
        buffer.append("]");

        if (maxlen instanceof org.python.types.NoneType) {
            return new org.python.types.Str("deque(" + buffer.toString() + ")");
        } else {
            org.python.types.Str length = ((org.python.types.Int) this.maxlen).__repr__();
            return new org.python.types.Str("deque(" + buffer.toString() + ", maxlen=" + length + ")");
        }
    }

    @org.python.Method(
            __doc__ = "Return len(self)."
    )
    public org.python.types.Int __len__() {
        return org.python.types.Int.getInt(this.linkedList.size());
    }

    @org.python.Method(
            __doc__ = "Return self==value.",
            args = {"other"}
    )
    public org.python.types.Bool __eq__(org.python.Object other) {
        if (!(other instanceof Deque)) {
            return org.python.types.Bool.FALSE;
        } else {
            boolean compare_maxlen = ((org.python.types.Bool) this.maxlen.__eq__(((Deque) other).maxlen)).value;
            boolean compare_list = this.linkedList.equals(((org.python.stdlib.collections.Deque) other).linkedList);
            return org.python.types.Bool.getBool(compare_maxlen && compare_list);
        }
    }

    @org.python.Method(
            __doc__ = "x.__getitem__(y) <==> x[y]",
            args = {"index"}
    )
    public org.python.Object __getitem__(org.python.Object index) {
        try {
            int idx;
            if (index instanceof org.python.types.Bool) {
                idx = (int) ((org.python.types.Bool) index).__int__().value;
            } else {
                idx = (int) ((org.python.types.Int) index).value;
            }
            if (idx < 0) {
                if (-idx > this._len()) {
                    throw new org.python.exceptions.IndexError("deque index out of range");
                } else {
                    return this.linkedList.get(this._len() + idx);
                }
            } else {
                if (idx >= this._len()) {
                    throw new org.python.exceptions.IndexError("deque index out of range");
                } else {
                    return this.linkedList.get(idx);
                }
            }
        } catch (ClassCastException e) {
            throw new org.python.exceptions.TypeError("sequence index must be integer, not '" + index.typeName() + "'");
        }
    }

    @org.python.Method(
            __doc__ = "Set self[key] to value.",
            args = {"index", "value"}
    )
    public void __setitem__(org.python.Object index, org.python.Object value) {
        try {
            int idx;
            if (index instanceof org.python.types.Bool) {
                idx = (int) ((org.python.types.Bool) index).__int__().value;
            } else {
                idx = (int) ((org.python.types.Int) index).value;
            }
            if (idx < 0) {
                if (-idx > this._len()) {
                    throw new org.python.exceptions.IndexError("deque index out of range");
                } else {
                    this.linkedList.set(this._len() + idx, value);
                }
            } else {
                if (idx >= this._len()) {
                    throw new org.python.exceptions.IndexError("deque index out of range");
                } else {
                    this.linkedList.set(idx, value);
                }
            }
        } catch (ClassCastException e) {
            throw new org.python.exceptions.TypeError("sequence index must be integer, not '" + index.typeName() + "'");
        }
    }

    @org.python.Method(
            __doc__ = "Delete self[key].",
            args = {"index"}
    )
    public void __delitem__(org.python.Object index) {
        try {
            int idx;
            if (index instanceof org.python.types.Bool) {
                idx = (int) ((org.python.types.Bool) index).__int__().value;
            } else {
                idx = (int) ((org.python.types.Int) index).value;
            }
            if (idx < 0) {
                if (-idx > this._len()) {
                    throw new org.python.exceptions.IndexError("deque index out of range");
                } else {
                    this.linkedList.remove(this._len() + idx);
                }
            } else {
                if (idx >= this._len()) {
                    throw new org.python.exceptions.IndexError("deque index out of range");
                } else {
                    this.linkedList.remove(idx);
                }
            }
        } catch (ClassCastException e) {
            throw new org.python.exceptions.TypeError("sequence index must be integer, not '" + index.typeName() + "'");
        }
    }

    @org.python.Method(
            __doc__ = "Implement iter(self)."
    )
    public org.python.Object __iter__() {
        org.python.types.Iterator iter = new org.python.types.Iterator();
        iter.iterator = this.linkedList.listIterator(0);
        return iter;
    }

    @org.python.Method(
            __doc__ = "Reverse the list in place and returns\n" +
                "an iterator that iterates over all the objects\n" +
                "in the list in reverse order. Does not\n" +
                "modify the original list."
    )
    public org.python.Object __reversed__() {
        org.python.types.ReverseIterator iter = new org.python.types.ReverseIterator();
        iter.iterator = this.linkedList.listIterator(this._len());
        return iter;
    }

    @org.python.Method(
            __doc__ = "Return key in self.",
            args = {"item"}
    )
    public org.python.Object __contains__(org.python.Object item) {
        return org.python.types.Bool.getBool(this.linkedList.contains(item));
    }

    @org.python.Method(
            __doc__ = "Return self+value.",
            args = {"other"}
    )
    public org.python.Object __add__(org.python.Object other) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.TypeError(
                "unsupported operand type(s) for +: 'collections.deque' and '" + other.typeName() + "'"
            );
        }

        if (other instanceof org.python.stdlib.collections.Deque) {
            org.python.stdlib.collections.Deque result = new org.python.stdlib.collections.Deque();
            result.linkedList = new java.util.LinkedList<org.python.Object>();
            result.maxlen = org.python.types.NoneType.NONE;
            result.linkedList.addAll(this.linkedList);
            result.linkedList.addAll(((org.python.stdlib.collections.Deque) other).linkedList);
            return result;
        } else {
            throw new org.python.exceptions.TypeError(
                "can only concatenate deque (not \"" + other.typeName() + "\") to deque"
            );
        }
    }

    @org.python.Method(
            __doc__ = "Return self*value.n",
            args = {"other"}
    )
    public org.python.Object __mul__(org.python.Object other) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.TypeError(
                "unsupported operand type(s) for *: 'collections.deque' and '" + other.typeName() + "'"
            );
        }

        if (other instanceof org.python.types.Int) {
            long count = ((org.python.types.Int) other).value;
            org.python.stdlib.collections.Deque result = new org.python.stdlib.collections.Deque();
            result.linkedList = new java.util.LinkedList<org.python.Object>();
            result.maxlen = org.python.types.NoneType.NONE;
            for (long i = 0; i < count; i++) {
                result.linkedList.addAll(this.linkedList);
            }
            return result;
        } else if (other instanceof org.python.types.Bool) {
            boolean count = ((org.python.types.Bool) other).value;
            org.python.stdlib.collections.Deque result = new org.python.stdlib.collections.Deque();
            result.linkedList = new java.util.LinkedList<org.python.Object>();
            result.maxlen = org.python.types.NoneType.NONE;
            if (count) {
                result.linkedList.addAll(this.linkedList);
            }
            return result;
        }
        throw new org.python.exceptions.TypeError(
            "can't multiply sequence by non-int of type '" + other.typeName() + "'"
        );
    }

    @org.python.Method(
            __doc__ = "Implement self*=value.",
            args = {"other"}
    )
    public org.python.Object __imul__(org.python.Object other) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.TypeError(
                "unsupported operand type(s) for *=: 'collections.deque' and '" + other.typeName() + "'"
            );
        }

        if (other instanceof org.python.types.Int) {
            long count = ((org.python.types.Int) other).value;
            org.python.stdlib.collections.Deque result = new org.python.stdlib.collections.Deque();
            result.linkedList = new java.util.LinkedList<org.python.Object>();
            result.maxlen = org.python.types.NoneType.NONE;
            for (long i = 0; i < count; i++) {
                result.linkedList.addAll(this.linkedList);
            }
            return result;
        } else if (other instanceof org.python.types.Bool) {
            boolean count = ((org.python.types.Bool) other).value;
            org.python.stdlib.collections.Deque result = new org.python.stdlib.collections.Deque();
            result.linkedList = new java.util.LinkedList<org.python.Object>();
            if (count) {
                result.linkedList.addAll(this.linkedList);
            }
            return result;
        }
        throw new org.python.exceptions.TypeError(
            "can't multiply sequence by non-int of type '" + other.typeName() + "'"
        );
    }

    @org.python.Method(
            __doc__ = "Add an element to the right side of the deque.",
            args = {"x"}
    )
    public void append(org.python.Object x) {
        this.linkedList.add(x);

        if (!(this.maxlen instanceof org.python.types.NoneType) &&
                this._len() > ((org.python.types.Int) this.maxlen).value) {
            this.linkedList.removeFirst();
        }
    }

    @org.python.Method(
            __doc__ = "Add an element to the left side of the deque.",
            args = {"x"}
    )
    public void appendleft(org.python.Object x) {
        this.linkedList.addFirst(x);

        if (!(this.maxlen instanceof org.python.types.NoneType) &&
                this._len() > ((org.python.types.Int) this.maxlen).value) {
            this.linkedList.removeLast();
        }
    }

    @org.python.Method(
            __doc__ = "Remove all elements from the deque."
    )
    public void clear() {
        this.linkedList.clear();
    }

    @org.python.Method(
            __doc__ = "Return a shallow copy of a deque."
    )
    public org.python.stdlib.collections.Deque copy() {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.AttributeError("'collections.deque' object has no attribute 'copy'");
        }

        org.python.stdlib.collections.Deque deque_copy = new org.python.stdlib.collections.Deque();
        deque_copy.maxlen = this.maxlen;
        deque_copy.linkedList = (java.util.LinkedList<org.python.Object>) this.linkedList.clone();

        return deque_copy;
    }

    @org.python.Method(
            __doc__ = "D.count(value) -> integer -- return number of occurrences of value",
            args = {"x"}
    )
    public org.python.Object count(org.python.Object x) {
        long counter = 0;

        java.util.Iterator<org.python.Object> iter = this.linkedList.descendingIterator();
        while (iter.hasNext()) {
            if (((org.python.types.Bool) x.__eq__(iter.next())).value) {
                counter += 1;
            }
        }

        return org.python.types.Int.getInt(counter);
    }

    @org.python.Method(
            __doc__ = "Extend the right side of the deque with elements from the iterable",
            args = {"iterable"}
    )
    public void extend(org.python.Object iterable) {
        org.python.Object iter = org.Python.iter(iterable);

        try {
            while (true) {
                this.append(iter.__next__());
            }
        } catch (org.python.exceptions.StopIteration e) {
        }
    }

    @org.python.Method(
            __doc__ = "Extend the left side of the deque with elements from the iterable",
            args = {"iterable"}
    )
    public void extendleft(org.python.Object iterable) {
        org.python.Object iter = org.Python.iter(iterable);

        try {
            while (true) {
                this.appendleft(iter.__next__());
            }
        } catch (org.python.exceptions.StopIteration e) {
        }
    }

    @org.python.Method(
            __doc__ =
                "D.index(value, [start, [stop]]) -> integer -- return first index of value.\n" +
                "Raises ValueError if the value is not present.",
            args = {"x"},
            varargs = "args"
    )
    public org.python.types.Int index(org.python.Object x, org.python.types.Tuple args) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.AttributeError("'collections.deque' object has no attribute 'index'");
        }

        int index = -1;

        if (args.value.isEmpty()) {
            index = this.linkedList.indexOf(x);
        } else {
            try {
                int start = (int) ((org.python.types.Int) args.value.get(0)).value;
                if (start < 0) {
                    start = Math.max(this._len() + start, 0);
                }

                int stop = this._len();
                if (args.value.size() > 1) {
                    stop = (int) ((org.python.types.Int) args.value.get(1)).value;
                    if (stop < 0) {
                        stop = Math.max(this._len() + stop, 0);
                    }
                }

                while (start < stop) {
                    try {
                        if (this.linkedList.get(start).equals(x)) {
                            index = start;
                            break;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                    start += 1;
                }
            } catch (ClassCastException e) {
                throw new org.python.exceptions.TypeError("slice indices must be integers or have an __index__ method");
            }
        }

        if (index == -1) {
            throw new org.python.exceptions.ValueError(x.__repr__() + " is not in deque");
        }

        return org.python.types.Int.getInt(index);
    }

    @org.python.Method(
            __doc__ = "D.insert(index, object) -- insert object before index",
            args = {"i", "x"}
    )
    public void insert(org.python.Object i, org.python.Object x) {
        if (org.Python.VERSION < 0x03050000) {
            throw new org.python.exceptions.AttributeError("'collections.deque' object has no attribute 'insert'");
        }

        if (!(this.maxlen instanceof org.python.types.NoneType) &&
                this._len() == ((org.python.types.Int) this.maxlen).value) {
            throw new org.python.exceptions.IndexError("deque already at its maximum size");
        } else {
            int index = (int) ((org.python.types.Int) i).value;
            if (index > this._len()) {
                // add to the end of deque
                index = this._len();
            } else if (index < 0) {
                index = Math.max(this._len() + index, 0);
            }
            this.linkedList.add(index, x);
        }
    }

    @org.python.Method(
            __doc__ = "Remove and return the rightmost element."
    )
    public org.python.Object pop() {
        if (this._len() == 0) {
            throw new org.python.exceptions.IndexError("pop from an empty deque");
        }

        return this.linkedList.removeLast();
    }

    @org.python.Method(
            __doc__ = "Remove and return the leftmost element."
    )
    public org.python.Object popleft() {
        if (this._len() == 0) {
            throw new org.python.exceptions.IndexError("pop from an empty deque");
        }

        return this.linkedList.removeFirst();
    }

    @org.python.Method(
            __doc__ = "D.remove(value) -- remove first occurrence of value.",
            args = {"value"}
    )
    public void remove(org.python.Object value) {
        if (!this.linkedList.remove(value)) {
            throw new org.python.exceptions.ValueError("deque.remove(x): x not in deque");
        }
    }

    @org.python.Method(
            __doc__ = "D.reverse() -- reverse *IN PLACE*"
    )
    public void reverse() {
        java.util.Collections.reverse(this.linkedList);
    }

    @org.python.Method(
            __doc__ = "Rotate the deque n steps to the right (default n=1).  If n is negative, rotates left.",
            args = {"n"}
    )
    public void rotate(org.python.Object n) {
        int step = (int) ((org.python.types.Int) n).value;
        boolean rotate_left = step < 0;

        if (!(step == 0 || this._len() == 0)) {
            if (rotate_left) {
                while (step < 0) {
                    this.append(this.popleft());
                    step += 1;
                }
            } else {
                while (step > 0) {
                    this.appendleft(this.pop());
                    step -= 1;
                }
            }
        }
    }
}
