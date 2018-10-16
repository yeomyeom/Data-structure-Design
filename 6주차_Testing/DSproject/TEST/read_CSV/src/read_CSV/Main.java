package read_CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException{
		int n=0;
		int a=0;
		String path = "CPU.csv";
		Scanner sc = new Scanner(new File(path));
		Scanner scan = new Scanner(System.in);
		//ArrayList<Integer> arr = new ArrayList<Integer>();
		//ArrayList<CPU> c_ar = new ArrayList<CPU>();
		
		input input = new input();
		
		sc.useDelimiter(",");
		a=sc.nextInt();
		CPU [] c_ar=new CPU[a];
		System.out.println(a);
		while(n<a) {
			c_ar[n]=new CPU();
			c_ar[n].index = sc.nextInt();
			System.out.println(c_ar[n].index);
			c_ar[n].point = sc.nextDouble();
			System.out.println(c_ar[n].point);
			c_ar[n].soket = sc.next();
			System.out.println(c_ar[n].soket);
			c_ar[n].tdp = sc.nextDouble();
			System.out.println(c_ar[n].tdp);
			c_ar[n].price = sc.nextDouble();
			System.out.println(c_ar[n].price);
			sc.next(); //pattern 규칙을 지키기 워한 꼼수

				/*System.out.println(sc.nextInt());//띄어쓰기 2가 들어가서 문제가 됨
				System.out.println(sc.nextDouble());
				System.out.println(sc.next());
				System.out.println(sc.nextDouble());
				System.out.println(sc.nextDouble());
				System.out.println(sc.next());*/
				n++;

			//System.out.println("n : " +n);
		}
		
		System.out.println("input banch point : ");
		input.point=scan.nextDouble();
		double choose=input.point;
		int index = 0;
		while(n==0) {
			if(input.point <= c_ar[n].point && choose<=c_ar[n].point)
			{
				choose = c_ar[n].point;
				index = n;
			}
			n--;
		}
		System.out.println("result point : "+choose+" and index : "+index);
		sc.close();
		scan.close();
	}
}
