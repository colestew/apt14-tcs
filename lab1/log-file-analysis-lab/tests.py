import unittest
import compute_highest_affinity
import randomized_input

class LogFileTest(unittest.TestCase):
    def test_1(self):
        site_list = ["a.com", "b.com", "a.com", "b.com", "a.com", "c.com"]
        user_list = ["andy", "andy", "bob", "bob", "charlie", "charlie"]
        time_list = [1238972321, 1238972456, 1238972618, 1238972899, 1248472489, 1258861829]

        computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
        expected_result = ("a.com", "b.com")

        self.failUnless(computed_result == expected_result)

    def test_random(self):
        num_lines = 10000
        num_users = 1000

        site_list = randomized_input.randomized_site_list(num_lines)
        user_list = randomized_input.randomized_user_list(num_lines, num_users)
        time_list = xrange(0,num_lines)

        computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
        expected_result = ("facebook", "google")

        assert computed_result == expected_result

    def test_3(self):

        a = "a"
        b = "b"
        c = "c"
        d = "d"
        e = "e"
        f = "f"
        g = "g"
        h = "h"
        
        A = "A"
        B = "B"
        C = "C"
        D = "D"
        E = "E"
        F = "F"
        G = "G"
        H = "H"
        
        
        user_list = [A, C, H, A, C, D, E, F, G, C, D, E, F, G, H, C, B, D, E, F, G, C, G, A, D, E, H, A, C, E, H]
        site_list = [a, a, a, b, b, b, b, b, b, c, c, c, c, c, c, d, e, e, e, e, e, f, f, g, g, g, g, h, h, h, h]
        
        time_list = range(0,30)
        
        computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
        expected_result = (b, c)
        
        self.failUnless(computed_result == expected_result)

    def test_4(self):
        a = "a"
        b = "b"
        c = "c"
        d = "d"
        e = "e"
        f = "f"
        g = "g"
        h = "h"
        
        A = "A"
        B = "B"
        C = "C"
        D = "D"
        E = "E"
        F = "F"
        G = "G"
        H = "H"
        
        
        site_list = [a,a,a,a,b,b,b,b,c,c,c,d,d,d,d,d]
        user_list = [B,D,E,F,A,B,C,F,B,D,E,A,B,C,D,F]
        
        time_list = range(0,16)
        
        computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
        expected_result = (b, d)
        
        self.failUnless(computed_result == expected_result)
        
    def test_5(self):
        a = "a"
        b = "b"
        c = "c"
        d = "d"
        e = "e"
        f = "f"
        g = "g"
        h = "h"
        
        A = "A"
        B = "B"
        C = "C"
        D = "D"
        E = "E"
        F = "F"
        G = "G"
        H = "H"
        
        
        site_list = [a,a,b,c,c,c,d,d,e,e,f,f,g]
        user_list = [B,D,C,A,B,C,B,C,C,D,A,B,A]
        
        time_list = range(0,13)
        
        computed_result = compute_highest_affinity.highest_affinity(site_list, user_list, time_list)
        expected_result1 = (c, d)
        expected_result2 = (c, f)
        
        self.failUnless(computed_result == expected_result1 or computed_result == expected_result2)

if __name__ == '__main__':
    unittest.main()
