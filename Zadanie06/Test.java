
public class Test
{
	public static void main(String[] args)
    {
        System.out.println("TEST1:");

        CompressionInterface test = new Compression();

        test.addWord("001");
        test.addWord("001");
        test.addWord("001");
        test.addWord("010");
        test.addWord("111");
        test.addWord("011");
        test.addWord("001");
        test.addWord("001");
        test.addWord("110");
        test.addWord("000");
        test.addWord("001");
        test.addWord("001");
        test.addWord("001");
        test.addWord("001");

        test.compress();

        System.out.println(test.getHeader());

        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());
        System.out.println(test.getWord());







        System.out.println("TEST2:");

        CompressionInterface test2 = new Compression();

        test2.addWord("000");
        test2.addWord("001");
        test2.addWord("000");
        test2.addWord("001");
        test2.addWord("000");
        test2.addWord("001");
        test2.addWord("000");
        test2.addWord("001");
        test2.addWord("011");
        test2.addWord("001");
        test2.addWord("000");
        test2.addWord("110");
        test2.addWord("001");
        test2.addWord("000");
        test2.addWord("111");
        test2.addWord("001");
        test2.addWord("001");
        test2.addWord("000");
        test2.addWord("000");
        test2.addWord("000");
        test2.addWord("001");

        test2.compress();

        System.out.println(test2.getHeader());

        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());
        System.out.println(test2.getWord());

    }
}
