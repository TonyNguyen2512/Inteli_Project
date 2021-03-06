package SE1501_SE151070;

import java.util.Scanner;
import java.util.Vector;
import java.io.*;
import java.util.Collections;

public class NewItems extends Vector<Item> {

    Scanner sc = new Scanner(System.in);
    Vector<String> storedCodes = new Vector<String>();

    public NewItems() {
        super();
    }

    public void loadStoredCodes(String fName) {
        if (storedCodes.size() > 0) {
            storedCodes.clear();
        }
        try {
            File f = new File(fName);
            if (!f.exists()) {
                return;
            }
            FileReader fr = new FileReader(f);
            BufferedReader bf = new BufferedReader(fr);
            String code, name, priceStr;
            while ((code = bf.readLine()) != null
                    && (name = bf.readLine()) != null
                    && (priceStr = bf.readLine()) != null) {
                storedCodes.add(code);
            }
            bf.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private boolean valid(String aCode) {
        for (int i = 0; i < storedCodes.size(); i++) {
            if (aCode.equals(storedCodes.get(i))) {
                return false;
            }
        }
        for (int i = 0; i < this.size(); i++) {
            if (aCode.equals(this.get(i).getCode())) {
                return false;
            }
        }
        return true;
    }

    private int find(String aCode) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getCode().equals(aCode)) {
                return i;
            }
        }
        return -1;
    }

    public void appendToFile(String fName) {
        if (this.size() == 0) {
            System.out.println("Empty List");
            return;
        }
        try {
            boolean append = true;
            File f = new File(fName);
            FileWriter fw = new FileWriter(f, append);
            PrintWriter pw = new PrintWriter(fw);
            for (Item x : this) {
                pw.println(x.getCode());
                pw.println(x.getName());
                pw.println(x.getAge());
                pw.println(x.getGender());
                pw.println(x.getScore());
                pw.flush();
            }
            pw.close();
            fw.close();
            this.loadStoredCodes(fName);
            this.clear();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void addNewItem() {
        String newCode, newName, gender;
        int age;
        double score;
        boolean duplicated = false, matched = true;
        System.out.println("Nh???p m?? sinh vi??n m???i: ");
        do {
            System.out.print("   ?????nh d???ng m?? Sinh Vi??n (S****): ");
            newCode = sc.nextLine().toUpperCase();
            duplicated = !valid(newCode);
            matched = newCode.matches("^S\\d{4}$");
            if (duplicated) {
                System.out.println(" M?? sinh vi??n ???? t???n t???i!.");
            }
            if (!matched) {
                System.out.println("  M?? Sinh vi??n bao g???m: 'S' v?? '4 k?? t??? kh??c'");
            }
        } while (duplicated || (!matched));
        System.out.print("   T??n: ");
        newName = sc.nextLine().toUpperCase();
        System.out.print("   Tu???i: ");
        age = Integer.parseInt(sc.nextLine());
        System.out.print("   Gi???i t??nh: ");
        gender = sc.nextLine().toUpperCase();
        System.out.print("   ??i???m: ");
        score = sc.nextDouble();
        this.add(new Item(newCode, newName, age, gender, score));
        System.out.println("Sinh vi??n m???i ???? ???????c th??m:");

    }

    public void removeItem() {
        String code;
        System.out.println("Nh???p m?? sinh vi??n mu???n x??a: ");
        code = sc.nextLine().toUpperCase();
        int pos = find(code);
        if (pos < 0) {
            System.out.println("M?? n??y kh??ng t???n t???i!");
        } else {
            this.remove(pos);
            System.out.println("Sinh vi??n " + code + " ???? ???????c x??a!");

        }
    }

    public void updateItem() {
        if (this.size() == 0) {
            System.out.println("DANH S??CH TR???NG.");
            return;
        }
        String code = null;
        String newName, newGender;
        int age = 0;
        double score = 0;
        System.out.print("Nh???p m?? sinh vi??n c???n ch???nh s???a: ");
        code = sc.nextLine().toUpperCase();
        int pos = find(code);
        if (pos < 0) {
            System.out.println("M?? n??y kh??ng t???n t???i!");
        } else {
            do {
                System.out.print(" T??n: ");
                newName = sc.nextLine().toUpperCase();
                if ("".equals(newName)) {
                    System.out.println("B???n kh??ng nh???p g??, vui l??ng nh???p l???i!");
                }
            } while ("".equals(newName));
            do {
                System.out.print(" Gi???i t??nh: ");
                newGender = sc.nextLine().toUpperCase();
                if ("".equals(newGender)) {
                    System.out.println("B???n kh??ng nh???p g??, vui l??ng nh???p l???i!");
                }
            } while ("".equals(newGender));
            int i;
            do {
                try {
                    System.out.print("   Tu???i: ");
                    age = Integer.parseInt(sc.nextLine());
                    if (age < 0 || age > 90) {
                        System.out.println("Nh???p sai, tu???i ph???i >=0 v?? <=90!");
                    }
                    i = 1;
                } catch (Exception e) {
                    System.out.println("Tu???i kh??ng h???p l???, vui l??ng th??? l???i!!");
                    i = -1;
                }
            } while (i == -1 || age < 0 || age > 90);
            do {
                try {
                    System.out.print(" ??i???m: ");
                    score = Double.parseDouble(sc.nextLine());
                    if (score < 0 || score > 10) {
                        System.out.println("Nh???p sai, ??i???m ph???i >=0 v?? <=10!");
                    }
                    i = 1;
                } catch (Exception e) {
                    System.out.println("??i???m kh??ng h???p l???, vui l??ng th??? l???i!!");
                    i = -1;
                }
            } while (i == -1 || score < 0 || score > 10);
            this.get(pos).setName(newName);
            this.get(pos).setGender(newGender);
            this.get(pos).setAge(age);
            this.get(pos).setScore(score);
            System.out.println("Sinh vi??n " + code + " ???? ???????c c???p nh???t!");
        }
    }

    public void print() {
        if (this.size() == 0) {
            System.out.println("DANH S??CH TR???NG.");
            return;
        }

        System.out.println("\nDANH S??CH SINH VI??N");
        System.out.println("----------------------------");
        for (Item x : this) {
            x.print();
        }
    }
}
