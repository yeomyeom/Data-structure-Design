package pcmaker;

public class CPU extends pc_parts{
	public class Node{
		int banchmark;//��ġ ����
		int tdp;//�Һ� ����
		String soket;//mainboard�� ����
		boolean overclock;//����Ŭ�� ����
		public Node(String a, String b, int c, int d, int f, String g, boolean h) {
				this.banchmark=d;
				this.tdp=f;
				this.soket=g;
				this.overclock=h;
		}
	}
}
