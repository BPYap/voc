from unittest import expectedFailure

from ..utils import TranspileTestCase


class ExceptionTests(TranspileTestCase):

    def test_raise(self):
        # Caught exception
        self.assertCodeExecution("""
            raise KeyError("This is the name")
            print('Done.')
            """, exits_early=True)

    def test_raise_without_any_params(self):
        self.assertCodeExecution("""
            raise ValueError()
            print('Done.')
        """, exits_early=True)

    def test_raise_by_classname(self):
        self.assertCodeExecution("""
            raise ValueError
            print('Done.')
        """, exits_early=True)

    def test_raise_catch(self):
        self.assertCodeExecution("""
            try:
                raise KeyError("This is the name")
                print("No error")
            except KeyError:
                print("Got a Key Error")
            print('Done.')
            """)

    def test_reraise(self):
        self.assertCodeExecution("""
            try:
                raise KeyError("This is the name")
                print("No error")
            except KeyError:
                print("Got error")
                raise
            print('Done.')
            """, exits_early=True)

    def test_reraise_anonymous(self):
        self.assertCodeExecution("""
            try:
                raise KeyError("This is the name")
                print("No error")
            except:
                print("Got error")
                raise
            print('Done.')
            """, exits_early=True)

    def test_reraise_named(self):
        self.assertCodeExecution("""
            try:
                raise KeyError("This is the name")
                print("No error")
            except KeyError as e:
                print("Got error")
                raise
            print('Done.')
            """, exits_early=True)

    def test_raise_custom_exception(self):
        self.assertCodeExecution("""
            class MyException(Exception):
                pass

            try:
                raise MyException()
            except MyException:
                print("Got a custom exception")
            print('Done.')
            """)

    @expectedFailure
    def test_raise_custom_exception_with_arg(self):
        self.assertCodeExecution("""
            class MyException(Exception):
                pass

            try:
                raise MyException("This is string argument")
            except MyException:
                print("Got a custom exception")
            print('Done.')
            """)

    def test_raising_exceptions_multiple_args(self):
        self.assertCodeExecution("""
            for exc in [KeyError, ValueError, TypeError]:
                try:
                    raise exc("one")
                except Exception as e:
                    print(e, e.args, str(e), repr(e))
                try:
                    raise exc("one", 2)
                except Exception as e:
                    print(e, e.args)
        """)

    def test_raise_nested_custom_exception(self):
        self.assertCodeExecution(
            """
            from example import *

            try:
                raise Parent.MyException()
            except Parent.MyException:
                print("Got a custom exception")
            print('Done.')
            """,
            extra_code={
                'example':
                    """
                    class Parent():
                        class MyException(Exception):
                            pass
                    """
            }, run_in_function=False)
            
        self.assertCodeExecution(
            """
            import example

            try:
                raise example.Parent.MyException()
            except example.Parent.MyException:
                print("example is not defined")
            print('Done.')
            """,
            extra_code={
                'example':
                    """
                    class Parent():
                        class MyException(Exception):
                            pass
                    """
            }, run_in_function=False)

        self.assertCodeExecution(
            """
            import example as custom

            try:
                raise custom.Parent.MyException()
            except custom.Parent.MyException:
                print("example is not defined")
            print('Done.')
            """,
            extra_code={
                'example':
                    """
                    class Parent():
                        class MyException(Exception):
                            pass
                    """
            }, run_in_function=False)

    def test_raise_custom_exception_import_from(self):
        self.assertCodeExecution(
            """
            from example import *

            try:
                raise MyException()
            except MyException:
                print("Got a custom exception")
            print('Done.')
            """,
            extra_code={
                'example':
                    """
                    class MyException(Exception):
                        pass

                    """
            }, run_in_function=False)

    def test_import_namespace(self):
        self.assertCodeExecution(
            """
            from example import *

            try:
                raise example.MyException()
            except:
                print("example is not defined")
            print('Done.')
            """,
            extra_code={
                'example':
                    """
                    class MyException(Exception):
                        pass

                    """
            }, run_in_function=False)

        self.assertCodeExecution(
            """
            import example

            try:
                raise MyException()
            except:
                print("MyException is not defined")
            print('Done.')
            """,
            extra_code={
                'example':
                    """
                    class MyException(Exception):
                        pass

                    """
            })
