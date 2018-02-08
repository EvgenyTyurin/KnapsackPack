/*

    Robert Lafore "Data Structures And Algorithms in Java"
    Chapter 6 Exercise 4: Knapsack problem and recursion

    by Evgeny Tyurin

*/

import java.util.ArrayList;
import java.util.Scanner;

public class Lafore6E4 {

    // Size of knapsack
    private static int bagSize;
    // Items to pack
    private static ArrayList<Integer> items = new ArrayList<>();
    // Packed items
    private static ArrayList<Integer> packedItems = new ArrayList<>();
    // Successful packing flag
    private static boolean packResult;
    // First packed item index of current pack attempt
    private static int startItem;

    // Recursive pack: try to move item into empty space in bag
    private static void pack(int itemIdx, int emptySpace) {

        // If itemIdx out of range - no suitable item found
        if (itemIdx >= items.size()) {
            // Empty bag, pick new start item
            packedItems.clear();
            startItem++;
            // If we already tried to start with every item,
            // pack impossible, else - start another pack
            if (startItem >= items.size()) {
                packResult = false;
                return;
            } else {
                pack(startItem, bagSize);
                return;
            }
        }

        // We have item to pack, let's try item at itemIdx
        int item = items.get(itemIdx);
        // If item fills bag - pack complete
        if (item == emptySpace) {
            packedItems.add(item);
            packResult = true;
            return;
        }
        // If item's too small - pack and try next,
        // else - do not pack and try to pack next item
        if (item < emptySpace) {
            packedItems.add(item);
            pack(itemIdx + 1, emptySpace - item);
        } else {
            pack(itemIdx + 1, emptySpace);
        }
    }

    // Run point
    public static void main(String[] args) {
        String answer;
        do {
            // User input section
            boolean inputOk = false;
            Scanner in = new Scanner(System.in);
            try {
                int itemN;
                do {
                    System.out.print("Enter number of items to pack: ");
                    itemN = in.nextInt();
                } while (itemN <= 0);
                for (int itemCount = 1; itemCount <= itemN; itemCount++) {
                    int item;
                    do {
                        System.out.print("Enter item №" + itemCount + ": ");
                        item = in.nextInt();
                    } while (item <= 0);
                    items.add(item);
                }
                System.out.print("Enter knapsack size: ");
                do {
                    bagSize = in.nextInt();
                } while (bagSize <= 0);
                inputOk = true;
            } catch (Exception e) {
                System.out.println("Input error!");
                in = new Scanner(System.in);
            }

            // If input data correct - try to pack
            if (inputOk) {
                pack(startItem, bagSize);
                // Print result
                System.out.println("Unpacked: " + items);
                if (packResult)
                    System.out.println("Packed: " + packedItems);
                else
                    System.out.println("Pack mission impossible");
            }

            // May be another pack?
            System.out.println("Enter 'y' to pack another sack:");
            answer = in.next();
            items.clear();
            packedItems.clear();
            startItem = 0;
        } while (answer.equals("y"));
    }

}
