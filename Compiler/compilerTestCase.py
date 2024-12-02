import unittest
from compiler import compile  # Replace 'your_module' with the actual name of your module

class TestCompileFunction(unittest.TestCase):

    def test_valid_colors(self):
        self.assertEqual(compile(True, "#ffffff", "#000000", "test.md"), "generated_pdf_from_md.pdf")

    def test_invalid_colors(self):
        self.assertEqual(compile(True, "#fff", "#000000", "test.md"), "a.pdf")
        self.assertEqual(compile(True, "#ffffff", "#000", "test.md"), "a.pdf")
        self.assertEqual(compile(True, "white", "#000000", "test.md"), "a.pdf")
        self.assertEqual(compile(True, "#ffffff", "black", "test.md"), "a.pdf")

    def test_valid_serifs(self):
        self.assertEqual(compile(True, "#ffffff", "#000000", "test.md"), "generated_pdf_from_md.pdf")
        self.assertEqual(compile(False, "#ffffff", "#000000", "test.md"), "generated_pdf_from_md.pdf")

    def test_invalid_serifs(self):
        self.assertEqual(compile("serif", "#ffffff", "#000000", "test.md"), "b.pdf")
        self.assertEqual(compile(None, "#ffffff", "#000000", "test.md"), "b.pdf")
        self.assertEqual(compile(1, "#ffffff", "#000000", "test.md"), "b.pdf")

if __name__ == '__main__':
    unittest.main()

