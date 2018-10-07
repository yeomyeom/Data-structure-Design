package pcmaker;

public class MB extends pc_parts{

	char [] soket;//cpu와 연결 조건
	char [] chipset;//메인보드 제품 버전 (H310, z370... 이런것들)
	double paze;//전원부 페이즈 수
	double memory_slot;//메모리 슬롯 수
	double ram_clock;//램 지원 클럭
	double function_number;//메인보드의 기능 갯수(추후 생략 가능)
	boolean over;//오버클럭 지원 여부
}
