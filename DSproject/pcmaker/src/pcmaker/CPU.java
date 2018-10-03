package pcmaker;

public class CPU extends pc_parts{
	public class Node{
		int banchmark;//벤치 점수
		int tdp;//소비 전력
		String soket;//mainboard와 접점
		boolean overclock;//오버클럭 여부
		public Node(String a, String b, int c, int d, int f, String g, boolean h) {
				this.banchmark=d;
				this.tdp=f;
				this.soket=g;
				this.overclock=h;
		}
	}
}
