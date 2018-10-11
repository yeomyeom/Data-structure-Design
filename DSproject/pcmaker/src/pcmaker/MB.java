package pcmaker;

public class MB extends pc_parts{

	String soket;//cpu와 연결 조건
	String chipset;//case와 연결 조건
	int memory_slot;//메모리 슬롯 수 ram과 연결 조건
	double ram_clock;//램 지원 클럭 ram과 연결 조건
	double paze;//페이즈 수 메인보드의 안정성
	int ddr_ver;// ddr 버전 ram과 연결 조건
	int sata;
	int over;//오버클럭 지원 여부 cpu와 연결 조건
}
