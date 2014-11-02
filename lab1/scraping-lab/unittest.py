import unittest
import yahoo_options_data

class ScrapingTest(unittest.TestCase):

    def test_f(self):
        computedJson = yahoo_options_data.contractAsJson("f.dat")
        expectedJson = open("f.json").read()
        expectedJson_change = open("f_change.json").read()

        self.failUnless(json.loads(computedJson) == json.loads(expectedJson) \
            and json.loads(computedJson) == json.loads(expectedJson_change))

    def test_aapl(self):
        computedJson = yahoo_options_data.contractAsJson("aapl.dat")
        expectedJson = open("aapl.json").read()
        expectedJson_change = open("aapl_change.json").read()

        self.failUnless(json.loads(computedJson) == json.loads(expectedJson) \
            and json.loads(computedJson) == json.loads(expectedJson_change))
    
    def test_xom(self):
        computedJson = yahoo_options_data.contractAsJson("xom.dat")
        expectedJson = open("xom.json").read()
        expectedJson_change = open("xom_change.json").read()

        self.failUnless(json.loads(computedJson) == json.loads(expectedJson) \
            and json.loads(computedJson) == json.loads(expectedJson_change))
    
if __name__ == '__main__':
    unittest.main()
