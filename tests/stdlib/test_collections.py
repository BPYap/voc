from unittest import expectedFailure

from ..utils import TranspileTestCase


class CollectionsModuleTests(TranspileTestCase):

    #######################################################
    # expected to fail in Python version 3.4 build because
    # it was not documented in Python version <= 3.4
    # # __doc__
    # def test___doc__(self):
        # self.assertCodeExecution("""
            # import collections
            # print(collections.__doc__)
            # """)

    #######################################################
    # __file__
    @expectedFailure
    def test___file__(self):
        self.assertCodeExecution("""
            import collections
            print(collections.__file__)
            """)

    #######################################################
    # __loader__
    @expectedFailure
    def test___loader__(self):
        self.assertCodeExecution("""
            import collections
            print(collections.__loader__)
            """)

    #######################################################
    # __name__
    def test___name__(self):
        self.assertCodeExecution("""
            import collections
            print(collections.__name__)
            """)

    #######################################################
    # __package__
    def test___package__(self):
        self.assertCodeExecution("""
            import collections
            print(collections.__package__)
            """)

    #######################################################
    # __spec__
    @expectedFailure
    def test___spec__(self):
        self.assertCodeExecution("""
            import collections
            print(collections.__spec__)
            """)

    #######################################################
    # namedtuple
    @expectedFailure
    def test_namedtuple(self):
        self.assertCodeExecution("""
            import collections
            print(collections.namedtuple('Point', ['x', 'y']))
            """)

    #######################################################
    # ChainMap
    @expectedFailure
    def test_ChainMap(self):
        self.assertCodeExecution("""
            import collections
            print(collections.ChainMap())
            """)

    #######################################################
    # Counter
    @expectedFailure
    def test_Counter(self):
        self.assertCodeExecution("""
            import collections
            print(collections.Counter())
            """)

    #######################################################
    # UserDict
    @expectedFailure
    def test_UserDict(self):
        self.assertCodeExecution("""
            import collections
            print(collections.UserDict())
            """)

    #######################################################
    # UserList
    @expectedFailure
    def test_UserList(self):
        self.assertCodeExecution("""
            import collections
            print(collections.UserList())
            """)

    #######################################################
    # UserString
    @expectedFailure
    def test_UserString(self):
        self.assertCodeExecution("""
            import collections
            print(collections.UserString("Hello World"))
            """)


class DequeTests(TranspileTestCase):

    def test_creation(self):
        self.assertCodeExecution("""
            import collections
            print(collections.deque())
            print(collections.deque([1, 2, 3]))
            print(collections.deque([1, 2, 3], maxlen=3))
            print(collections.deque([1, 2, 3], maxlen=2))
            print(collections.deque([1, 2, 3], maxlen=0))
            """)

    def test_invalid_argument(self):
        self.assertCodeExecution("""
            import collections
            try:
                d = collections.deque(iterable=123)
                print("should not print this")
            except TypeError as e:
                print(e)

            try:
                d = collections.deque(maxlen='abc')
                print("should not print this")
            except TypeError as e:
                print(e)
            """)

    def test_append(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d)
            d.append(5)
            print(d)

            d = collections.deque([1, 2, 3], maxlen=3)
            print(d)
            d.append(5)
            print(d)
            """)

    def test_append_left(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d)
            d.appendleft(5)
            print(d)

            d = collections.deque([1, 2, 3], maxlen=3)
            print(d)
            d.appendleft(5)
            print(d)
            """)

    def test_clear(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d)
            d.clear()
            print(d)
            """)

    def test_copy(self):
        self.assertCodeExecution("""
            import collections

            d1 = collections.deque([1, 2, 3])

            try:
                d2 = d1.copy()
                print("d1:", d1)
                print("d2:", d2)

                print(d1 == d2)
                print(d1 is d2)

                d2.append(5)
                print("d1:", d1)
                print("d2:", d2)
                print(d1 == d2)
                print(d1.popleft() == d2.popleft())
            except AttributeError as e:
                # not implemented in Python version < 3.5
                print(e)
            """)

    def test_count(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 1, 1, 2, 3])
            print(d.count(1))
            """)

    def test_extend(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            d.extend([4, 5, 6])
            print(d)

            d = collections.deque([1, 2, 3], maxlen=2)
            d.extend([4, 5, 6])
            print(d)
            """)

    def test_extendleft(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            d.extendleft([4, 5, 6])
            print(d)

            d = collections.deque([1, 2, 3], maxlen=2)
            d.extendleft([4, 5, 6])
            print(d)
            """)

    def test_index(self):
        self.assertCodeExecution("""
            import collections

            try:
                d = collections.deque([1, 2, 3, 4, 5, 4, 3, 2, 1])
                print(d.index(3))
                print(d.index(3, 3))
                print(d.index(3, 6, 8))
                print(d.index(3, -3))
                print(d.index(3, -5, 7))
                print(d.index(3, -5, -1))
                print(d.index(3, -100, 100))

                try:
                    print(d.index("a"))
                    print("should not print this")
                except ValueError as e:
                    print(e)

                try:
                    print(d.index(3, 100))
                    print("should not print this")
                except ValueError as e:
                    print(e)

                try:
                    print(d.index(3, 100, 100))
                    print("should not print this")
                except ValueError as e:
                    print(e)

                try:
                    print(d.index(3, 100, -100))
                    print("should not print this")
                except ValueError as e:
                    print(e)

                try:
                    print(d.index(3, -100, -100))
                    print("should not print this")
                except ValueError as e:
                    print(e)

                try:
                    print(d.index(3, "a"))
                    print("should not print this")
                except TypeError as e:
                    print(e)

                try:
                    print(d.index(3, 0, "a"))
                    print("should not print this")
                except TypeError as e:
                    print(e)
            except AttributeError as e:
                # not implemented in Python version < 3.5
                print(e)
            """)

    def test_insert(self):
        self.assertCodeExecution("""
            import collections

            try:
                d = collections.deque([1, 2, 5])
                d.insert(0, -1)
                print(d)
                d.insert(1, 0)
                print(d)
                d.insert(-1, 4)
                print(d)
                d.insert(-2, 3)
                print(d)
                d.insert(100, 6)
                print(d)
                d.insert(-100, -2)
                print(d)

                d = collections.deque([1, 2, 3], maxlen=3)
                try:
                    d.insert(5, 1)
                    print("should not print this")
                except IndexError as e:
                    print(e)
            except AttributeError as e:
                # not implemented in Python version < 3.5
                print(e)
            """)

    def test_pop(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d.pop())

            d = collections.deque()
            try:
                d.pop()
                print("should not print this")
            except IndexError as e:
                print(e)
            """)

    def test_popleft(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d.popleft())

            d = collections.deque()
            try:
                d.popleft()
                print("should not print this")
            except IndexError as e:
                print(e)
            """)

    def test_remove(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d.remove(3))

            try:
                d.remove('a')
                print("should not print this")
            except ValueError as e:
                print(e)
            """)

    def test_reverse(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d.reverse())
            print(d)

            d = collections.deque()
            print(d.reverse())
            print(d)
            """)

    def test_rotate(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d.rotate(10))
            print(d)

            d = collections.deque([1, 2, 3])
            print(d.rotate(-10))
            print(d)

            d = collections.deque([1, 2, 3])
            print(d.rotate(0))
            print(d)

            d = collections.deque()
            print(d.rotate(10))
            print(d)
            """)

    def test_maxlen_attr(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d.maxlen)
            
            d = collections.deque(maxlen=10)
            print(d.maxlen)
            """)

    def test_iter(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3, 4, 5])

            for i in d:
                print(i)

            for i in iter(d):
                print(i)
            """)

    def test_len(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(len(d))

            d = collections.deque(maxlen=10)
            print(len(d))
            """)

    def test_reversed(self):
        self.assertCodeExecution("""
            import collections
            
            d = collections.deque([1, 2, 3, 4, 5])
            for i in reversed(d):
                print(i)
            """)

    def test_contains(self):
        self.assertCodeExecution("""
            import collections
            d = collections.deque([1, 2, 3, 4, 5])
            print(1 in d)
            print('a' in d)
            """)

    def test_getitem(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            print(d[0])
            print(d[-1])

            try:
                print(d[0:1:1])
                print("should not print this")
            except TypeError as e:
                print(e)

            try:
                print(d['a'])
                print("should not print this")
            except TypeError as e:
                print(e)

            try:
                print(d[-10])
                print("should not print this")
            except IndexError as e:
                print(e)

            try:
                print(d[10])
                print("should not print this")
            except IndexError as e:
                print(e)
            """)

    def test_setitem(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            d[0] = 'a'
            print(d)
            d[-1] = 'c'
            print(d)

            try:
                d['a'] = 1
                print("should not print this")
            except TypeError as e:
                print(e)

            try:
                d[-10] = 1
                print("should not print this")
            except IndexError as e:
                print(e)

            try:
                d[10] = 1
                print("should not print this")
            except IndexError as e:
                print(e)
            """)

    def test_delitem(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            del d[0]
            print(d)
            del d[-1]
            print(d)

            try:
                del d['a']
                print("should not print this")
            except TypeError as e:
                print(e)

            try:
                del d[-10]
                print("should not print this")
            except IndexError as e:
                print(e)

            try:
                del d[10]
                print("should not print this")
            except IndexError as e:
                print(e)
            """)

    def test_add(self):
        self.assertCodeExecution("""
            import collections

            try:
                d1 = collections.deque([1, 2, 3])
                d2 = collections.deque([4, 5, 6])
                print(d1 + d2)

                try:
                    print(d1 + "string")
                    print("should not print this")
                except TypeError as e:
                    print(e)

            except TypeError as e:
                # not implemented in Python version < 3.5
                print(e)

            # TODO: this can be moved into try-catch suite above once 
            #       #900 is resolved
            try:
                print(d1 + 123)
                print("should not print this")
            except TypeError as e:
                print(e)
            """)

    def test_mul(self):
        self.assertCodeExecution("""
            import collections

            d = collections.deque([1, 2, 3])
            try:
                print(d * 5)
                print(d * -5)
                print(5 * d)
                print(d * 123)

                try:
                    print(d * "string")
                    print("should not print this")
                except TypeError as e:
                    print(e)

            except TypeError as e:
                # not implemented in Python version < 3.5
                print(e)
            """)

    def test_imul(self):
        self.assertCodeExecution("""
            import collections

            try:
                d = collections.deque([1, 2, 3])
                d *= 5
                print(d)
                d *= -1
                print(d)

                try:
                    d *= "string"
                    print("should not print this")
                except TypeError as e:
                    print(e)
            except TypeError as e:
                # not implemented in Python version < 3.5
                print(e)
                
            # TODO: this can be moved into try-catch suite above once 
            #       #900 is resolved
            try:
                d *= 123
                print("can't see this")
            except TypeError as e:
                print(e)
            """)


class OrderedDictTests(TranspileTestCase):

    pass


class DefaultDictTests(TranspileTestCase):

    def test_creation(self):
        self.assertCodeExecution("""
            import collections
            print(collections.defaultdict())
            print(collections.defaultdict(int))
            print(collections.defaultdict(list, {'a': 1}))
            """)

    def test_invalid_first_argument(self):
        self.assertCodeExecution("""
            import collections
            try:
                d = collections.defaultdict(123)
                print("can't see this")
            except TypeError as e:
                print(e)
            """)

    def test_dict_method(self):
        self.assertCodeExecution("""
            import collections
            d = collections.defaultdict(int)
            print(d.get("key"))
            print(d.setdefault("default"))
            print(d)
            """)

    def test_default_list(self):
        self.assertCodeExecution("""
            from collections import defaultdict

            s = [('yellow', 1), ('blue', 2), ('yellow', 3), ('blue', 4), ('red', 1)]
            d = defaultdict(list)
            for k, v in s:
                d[k].append(v)
            print(sorted(d.items()))
            """)

    def test_default_int(self):
        self.assertCodeExecution("""
            from collections import defaultdict

            s = 'mississippi'
            d = defaultdict(int)
            for k in s:
                d[k] += 1
            print(sorted(d.items()))
            """)

    def test_default_callable(self):
        self.assertCodeExecution("""
            from collections import defaultdict

            def constant_factory(value):
                return lambda: value
            d = defaultdict(constant_factory('<missing>'))
            d.update(name='John', action='ran')
            print('%(name)s %(action)s to %(object)s' % d)
            """)

    def test_default_set(self):
        self.assertCodeExecution("""
            from collections import defaultdict

            s = [('red', 1), ('blue', 2), ('red', 3), ('blue', 4), ('red', 1), ('blue', 4)]
            d = defaultdict(set)
            for k, v in s:
                d[k].add(v)
            print(sorted(d.items()))
            """)
