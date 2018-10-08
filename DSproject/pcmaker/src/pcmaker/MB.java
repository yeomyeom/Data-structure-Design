package pcmaker;

public class MB extends pc_parts{

	String soket;//cpu와 연결 조건
	String chipset;//case와 연결 조건
	double paze;//전원부 페이즈 수
	int memory_slot;//메모리 슬롯 수
	double ram_clock;//램 지원 클럭
	int ddr_ver;// ddr 버전 RAM과 연결 조건
	int sata;
	double function_number;//메인보드의 기능 갯수(추후 생략 가능)
	int over;//오버클럭 지원 여부
}
