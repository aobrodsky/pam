import java.net.*;
import java.io.*;
import java.util.*;

public class Pam {
  private static boolean DEBUG = false;  // set to true to get reams of output
  private static int     NUM_OPS = 100000; // number of ops to be perform
  private static int     PROGRESS = 1000; // number of ops between dots
  private static Random  random = new Random(new Date().getTime());


  public static void main(String [] args) {

    try {
      if (args.length < 2) {
        Scanner sc = new Scanner(System.in);
        args = new String[2];
        System.out.print("Enter the class you wish to test: ");
        args[0] = sc.nextLine();
        System.out.print("Enter the number of items to use (test size): ");
        args[1] = sc.nextLine();
      }

      int num = Integer.parseInt(args[1]);
      if (num < 1) {
        System.out.println("Error: The 2nd parameter must be positive");
        return;
      }

      String name = args[0];
      System.out.println("================================ Testing " + name);
      System.out.flush();

      URL [] u = {new URL("file://./")};
      ClassLoader cl = new URLClassLoader(u);
      AbstractCollection<String> m = 
                          (AbstractCollection) cl.loadClass(name).newInstance();
  
      test(m, makeWords(new String[num*2]), num);    
    } catch (MalformedURLException m) {
      System.out.println("Error: Class \"" + args[0] + "\" not found");
    } catch (ClassNotFoundException c) {
      System.out.println("Error: Class \"" + args[0] + "\" not found");
    } catch (InstantiationException i) {
      System.out.println("Instantiation Exception");
    } catch (IllegalAccessException i) {
      System.out.println("Illegal Access Exception");
    } catch (NumberFormatException i) {
      System.out.println("Error: Third parameter must be a positive integer");
    }
  }


  private static String [] makeWords(String [] words) {
    
    char [] word = new char[32];

    for (int i = 0; i < words.length; i++) {
      for (int j = 0; j < 32; j++) {
        word[j] = (char)('a' + random.nextInt(26));
      }
      words[i] = new String(word);
    }

    return words;
  }


  public static void test(AbstractCollection m, String [] notinmap, int num) {
   
      int nm_size = notinmap.length;
      String [] inmap = new String[notinmap.length];

      System.out.print("================================ Loading ");
      // load specified number of entries into the map before performing test
      long t = new Date().getTime();
      int im_size = 0;
      for (int i = 0; i < num; i++) {
        // add word to inmap
        nm_size--;
        inmap[im_size] = notinmap[nm_size];

        // insert word into map
        if (DEBUG) {
          System.out.println("Inserting: " + notinmap[nm_size]);
        }
        m.add(notinmap[nm_size]);
        im_size++;
      }
      t = new Date().getTime() - t;
      long total = t;
      System.out.println("-- task time: " + t + "ms");

      System.out.println("================================= Performing Test");
      t = new Date().getTime();
      for (int i = 0; i < NUM_OPS; i++) {
        // print progress marker every PROGRESS iterations
        if (i % PROGRESS == 0) {
          System.out.print(".");
          System.out.flush();
        }

        // randomly perform insert, find, or remove
        int j;
        switch(random.nextInt(3)) {
        case 0:
          // check if we can insert another item
          if ((im_size > 3 * num) || (nm_size < 3)) {
            break;
          }

          // move item to from notinmap to inmap
          nm_size--;
          inmap[im_size] = notinmap[nm_size];

          // perform insert
          if (DEBUG) {
            System.out.println("Inserting: " + notinmap[nm_size] );
          }
          m.add(notinmap[nm_size]);
          im_size++;
          break;

        case 1: 
          // select item to search for
          if (im_size < 1) {
            break;
          } else if (im_size == 1) {
            j = 0;
          } else {
            j = random.nextInt(im_size);
          }

          // perform search on map
          if (DEBUG) {
            System.out.println("Searching for: " + inmap[j]);
          }

          if (!m.contains(inmap[j])) {
            System.out.println("Ooops: Your collection is broken.  Aborting!");
            return;
          }
          break;

        case 2:
          // select item from inmap to remove
          if (im_size < 1) {
            break;
          } else if (im_size == 1) {
            j = 0;
          } else {
            j = random.nextInt(im_size);
          }

          // remove item from map and try to find it again
          if (DEBUG) {
            System.out.println("Removing: " + inmap[j]);
          }

          m.remove(inmap[j]);

          // remove word from inmap 
          String s = inmap[j];
          im_size--;
          inmap[j] = inmap[im_size];

          // move word into notinmap
          j = random.nextInt(nm_size);
          notinmap[nm_size] = notinmap[j];
          notinmap[j] = s;
          nm_size++;

          break;
        }
      }

      t = new Date().getTime() - t;
      total = total + t;
      System.out.println("");
      System.out.println("================================ Done time: " + t + 
                           "ms");
      System.out.println("================================ Total time: " +
                         total + "ms");
  }
}
