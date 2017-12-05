import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution {

    private static class Node implements Comparable<Node> {

        public static final int SUBTRACTION = 0;
        public static final int RATIO = 1;

        private final int weight;
        private final int length;
        private final int index;
        private final int orderingType;

        public Node(int w, int l, int i, int o) {
            weight = w;
            length = l;
            index = i;
            orderingType = o;
        }

        public int getLength() {
            return length;
        }

        public int wct(int accumulatedLength) {
            return weight * (length + accumulatedLength);
        }

        private int compareSubtractionTo(Node that) {
            if ((weight - length) > (that.weight - that.length)) {
                return -1;
            }
            if ((weight - length) < (that.weight - that.length)) {
                return 1;
            }
            if (weight > that.weight) {
                return -1;
            }
            if (weight < that.weight) {
                return 1;
            }
            return 0;
        }

        private int compareRatioTo(Node that) {
            if (((double) weight / (double) length) > ((double) that.weight
                    / (double) that.length)) {
                return -1;
            }
            if (((double) weight / (double) length) < ((double) that.weight
                    / (double) that.length)) {
                return 1;
            }
            return 0;
        }

        @Override
        public int compareTo(Node that) {
            if (orderingType == SUBTRACTION) {
                return compareSubtractionTo(that);
            }

            return compareRatioTo(that);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("i: ").append(index).append(", w: ").append(Integer.toString(weight))
                    .append(", l: ").append(Integer.toString(length)).append(", wct: ");
            if (orderingType == SUBTRACTION) {
                sb.append(Integer.toString(weight - length));
            } else {
                sb.append(String.format("%.3f", (double) weight/(double) length));
            }

            return sb.toString();
        }

    }

    public static void main(String[] args) {

        // check command line arguments
        if (args.length < 1) {
            System.out.println("Need one parameter (for instance jobs.txt)");
            return;
        }

        // open file
        File file = new File(args[0]);

        Scanner in;
        try {
            in = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // read its content
        int n = in.nextInt();

        // while reading insert nodes into the priority queue
        PriorityQueue<Node> pqS = new PriorityQueue<Node>();
        PriorityQueue<Node> pqR = new PriorityQueue<Node>();
        for (int i = 0; i < n; i++) {
            int weight = in.nextInt();
            int length = in.nextInt();
            pqS.add(new Node(weight, length, i, Node.SUBTRACTION));
            pqR.add(new Node(weight, length, i, Node.RATIO));
        }

        in.close();

        Node node;
        double totalWct = 0;
        int accumulatedLength = 0;
        // pool items from priority queue and accumulate total weighted
        // completion time
        int count = 10000;
        while (((node = pqS.poll()) != null) && (count != 0)) {
            accumulatedLength += node.getLength();
            totalWct += node.wct(accumulatedLength);
            StringBuilder sb = new StringBuilder(node.toString());
            sb.append(" // ").append(String.format("%.0f", totalWct));
//            System.out.println(sb.toString());
            count--;
        }

        System.out.println(
                "The total sum of weighted completion time is: " + String.format("%.0f", totalWct));

        totalWct = 0;
        accumulatedLength = 0;
        count = 10000;
        while (((node = pqR.poll()) != null) && (count != 0)) {
            accumulatedLength += node.getLength();
            totalWct += node.wct(accumulatedLength);
            StringBuilder sb = new StringBuilder(node.toString());
            sb.append(" // ").append(String.format("%.0f", totalWct));
//            System.out.println(sb.toString());
            count--;
        }

        System.out.println(
                "The total sum of weighted completion time is: " + String.format("%.0f", totalWct));

    }

}
