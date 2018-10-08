package pcmaker;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) throws java.io.FileNotFoundException
	{
		spec input = new spec();
		Scanner scan = new Scanner(System.in);
		String path;
		int result[] = new int [9];
		int choose;
		String edge="";
		int overclock=0;
		double edge_tdp =0.0;
		double edge_tdp_2=0.0;
		
		//user input
		System.out.print("CPU banchmark point : ");
		input.cpu = scan.nextDouble();
		System.out.print("GPU banchmark point : ");
		input.gpu = scan.nextDouble();
		System.out.print("Ram volume : ");
		input.ram_vol = scan.nextDouble();
		System.out.print("Cooling or silence : ");
		input.cool=scan.nextInt();
		scan.close();
		///////////////////////////////////////////////////////////////////////
		path = "GPUDB.csv";//GPU 노드 생성 시작
		Scanner database = new Scanner(new File(path));
		database.useDelimiter(",");
		int a = database.nextInt();
		GPU gpu_arr[] = new GPU[a];
		int i=0;
		int p=0;
		int n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextInt();
			if(input.gpu<p)
			{
				gpu_arr[i]=new GPU();
				gpu_arr[i].point=p;							//밴치점수
				gpu_arr[i].price=database.nextDouble();		//가격
				gpu_arr[i].tdp=database.nextDouble();		//소비전력
				for(int tmp=0;tmp<9;tmp++)
				{
					database.next();
				}
				gpu_arr[i].index=n;//나중에 DB에서 찾을 인덱스 값
				i++;
			}else
			{
				for(int tmp =0; tmp<11;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int gpu_arr_size = i;
		database.close();//GPU 노드 생성 종료
		if(gpu_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong GPU banchmark point");
			scan.close();
			return;
		}
		else
		{
			double point=(gpu_arr[0].point/gpu_arr[0].price);
			choose=0;
			for(int tmp=1; tmp<gpu_arr_size;tmp++)//GPU 선택
			{
				if(point<(gpu_arr[tmp].point/gpu_arr[tmp].price))
				{
					choose = gpu_arr[tmp].index;
					edge_tdp = gpu_arr[tmp].tdp;
				}
			}
			result[0]=choose;//최종 결과물 출력때 GPUDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}

		////////////////////////////////////////////////////////////////////////
		path = "CPUDB.csv";//CPU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CPU 갯수
		CPU cpu_arr [] = new CPU[a];
		i=0;
		p=0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextInt();
			if(input.cpu<p)//cpu 밴치점수가 사용자 입력값보다 크다면
			{
				cpu_arr[i] =new CPU();
				cpu_arr[i].point = p;						//밴치점수
				cpu_arr[i].price = database.nextDouble();	//가격
				cpu_arr[i].tdp = database.nextDouble();		//사용 전력 파워 고를때 참조함
				cpu_arr[i].over = database.nextInt();		//오버클럭 여부 메인보드 선택할때 참조함
				cpu_arr[i].soket = database.next();			//소켓 버전 메인보드 선택할때 참조함
				for(int tmp=0;tmp<12;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				cpu_arr[i].index=n;
				i++;
			}
			else
			{
				for(int tmp=0;tmp<16;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int cpu_arr_size = i;
		database.close();//CPU 노드 생성 끝
		if(cpu_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong CPU banchmark point");
			scan.close();
			return;
		}
		else
		{
			double point=(cpu_arr[0].point/cpu_arr[0].price);
			choose=0;
			for(int tmp=1; tmp<cpu_arr_size;tmp++)//GPU 선택
			{
				if(point<(cpu_arr[tmp].point/cpu_arr[tmp].price))
				{
					choose = cpu_arr[tmp].index;
					edge = cpu_arr[tmp].soket;//CPU, MB간 엣지 연결
					overclock = cpu_arr[tmp].over;//오버클록 가능 여부
					edge_tdp_2=cpu_arr[tmp].tdp;
				}
			}
			result[1]=choose;//최종 결과물 출력때 CPUDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		/////////////////////////////////////////////////////////////////////////
		path="MBDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a=database.nextInt();
		MB mb_arr[]=new MB[a];//MB 노드 생성
		i=0;
		p=0;
		n=1;
		while(database.nextInt()!=0)
		{
			if(edge.equals(database.next()))//CPU, MB 엣지 연결
			{
				mb_arr[i] =new MB();
				mb_arr[i].price = database.nextInt();			//가격
				mb_arr[i].memory_slot = database.nextInt();	//메모리 슬롯 갯수 MB RAM간 연결 접점
				mb_arr[i].ram_clock = database.nextDouble();	//램 클록 수 MB RAM간 연결 접점
				mb_arr[i].ddr_ver = database.nextInt();			//램 ddr 버전 MB RAM간 연결 접점
				database.next();			//sata 3가 몇개 연결 가능한가 MB HDDSSD간 연결 접점
				database.next();			//메인보드 추가기능 
				database.next();
				mb_arr[i].chipset = database.next();			//MB와 CASE간 엣지 연결 조건
				for(int tmp=0;tmp<7;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				mb_arr[i].index=n;
				i++;
			}else
			{
				for(int tmp=0;tmp<15;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int mb_arr_size = i;
		database.close();
		int edge_ram1=0;
		double edge_ram2=0.0;
		int edge_ram3=0;
		String edge_case ="";
		if(mb_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no MB soket matched CPU");
			scan.close();
			return;
		}
		else
		{
			double point=(mb_arr[0].ram_clock/mb_arr[0].price);
			choose=0;
			for(int tmp=1; tmp<mb_arr_size;tmp++)//GPU 선택
			{
				if(point<(mb_arr[tmp].ram_clock/mb_arr[tmp].price))
				{
					choose = mb_arr[tmp].index;
					edge_ram1 = mb_arr[tmp].memory_slot;//MB,ram간 엣지 연결
					edge_ram2 = mb_arr[tmp].ram_clock;//MB,ram간 엣지 연결
					edge_ram3 = mb_arr[tmp].ddr_ver;//MB,ram간 엣지 연결
					edge_case = mb_arr[tmp].chipset;//MB case간 엣지 연결
				}
			}
			result[2]=choose;//최종 결과물 출력때 MBDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "RAMDB.csv";//CPU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CPU 갯수
		RAM ram_arr [] = new RAM[a];
		i=0;
		p=0;
		int q=0;
		double r=0.0;
		int l=0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextInt();//용량
			q=database.nextInt();//갯수
			r=database.nextDouble();//클락
			l=database.nextInt();//ddr 버전
			if(input.ram_vol<p && edge_ram1 < q && edge_ram2 < r && edge_ram3 == l)//Ram 용량이 사용자 입력값보다 크다면
			{
				ram_arr[i] =new RAM();
				ram_arr[i].vol = p;							//램용량
				ram_arr[i].ram_number = q;	//램갯수
				ram_arr[i].ram_clock = r;		//오버클럭 여부 메인보드 선택할때 참조함
				ram_arr[i].ddr_ver = l;			//소켓 버전 메인보드 선택할때 참조함
				ram_arr[i].price = database.nextDouble();	//가격
				for(int tmp=0;tmp<8;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				ram_arr[i].index=n;
				i++;
			}
			else
			{
				for(int tmp=0;tmp<9;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int ram_arr_size = i;
		database.close();//CPU 노드 생성 끝
		if(ram_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no RAM matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=((ram_arr[0].vol*ram_arr[0].ram_clock)/ram_arr[0].price);
			choose=0;
			for(int tmp=1; tmp<ram_arr_size;tmp++)//GPU 선택
			{
				if(point<(ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price)
				{
					choose = ram_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
				}
			}
			result[3]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "SSDDB.csv";//SSD 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 SSD 갯수
		SSD ssd_arr [] = new SSD[a];
		i=0;
		p=0;
		while(database.nextInt()!=0)
		{
			ssd_arr[i] =new SSD();
			ssd_arr[i].rspeed = database.nextDouble();	//읽기속도
			ssd_arr[i].wspeed = database.nextDouble();	//쓰기 속도
			ssd_arr[i].as = database.nextDouble();		//as기간
			ssd_arr[i].vol = database.nextDouble();     //용량
			ssd_arr[i].price = database.nextDouble();   //가격
			for(int tmp=0;tmp<12;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
			{
				database.next();
			}
			ssd_arr[i].index=i+1;
			i++;
		}
		int ssd_arr_size = i;
		database.close();//CPU 노드 생성 끝
		if(ssd_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no SSD matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=ssd_arr[0].as*ssd_arr[0].vol*(ssd_arr[0].rspeed+ssd_arr[0].wspeed)/ssd_arr[0].price;
			choose=0;
			for(int tmp=1; tmp<ssd_arr_size;tmp++)//GPU 선택
			{
				if(point<ssd_arr[tmp].as*ssd_arr[tmp].vol*(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)/ssd_arr[tmp].price)
				{
					choose = ssd_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
				}
			}
			result[4]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "HDDDB.csv";//HDD 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 HDD 갯수
		HDD hdd_arr [] = new HDD[a];
		i=0;
		p=0;
		while(database.nextInt()!=0)
		{
			hdd_arr[i] =new HDD();
			hdd_arr[i].vol = database.nextDouble();	//용량
			hdd_arr[i].rpm = database.nextDouble();	//쓰기 속도
			hdd_arr[i].price = database.nextDouble();   //가격
			for(int tmp=0;tmp<7;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
			{
				database.next();
			}
			hdd_arr[i].index=i+1;
			i++;
		}
		int hdd_arr_size = i;
		database.close();//CPU 노드 생성 끝
		if(hdd_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no HDD matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price;
			choose=0;
			for(int tmp=1; tmp<hdd_arr_size;tmp++)//GPU 선택
			{
				if(point<hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price)
				{
					choose = hdd_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
				}
			}
			result[5]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "PSUDB.csv";//PSU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 SSD 갯수
		PSU psu_arr [] = new PSU[a];
		i=0;
		p=0;
		n=1;
		double k=0.0;
		while(database.nextInt()!=0)
		{
			k=database.nextDouble();
			if((edge_tdp+edge_tdp_2)*1.2<k)//정격용량이 CPU+GPU *1.2가 되야 안전하다
			{
				psu_arr[i] =new PSU();
				psu_arr[i].power = k;
				psu_arr[i].as = database.nextDouble();						
				psu_arr[i].price = database.nextDouble();	
				for(int tmp=0;tmp<18;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				psu_arr[i].index=n;
				i++;
			}
			else
			{
				for(int tmp=0;tmp<20;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int psu_arr_size = i;
		database.close();//psu 노드 생성 끝
		if(psu_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no PSU matched CPU+GPU");
			scan.close();
			return;
		}
		else
		{
			double point=(psu_arr[0].power*psu_arr[0].as)/psu_arr[0].price;
			choose=0;
			for(int tmp=1; tmp<psu_arr_size;tmp++)//GPU 선택
			{
				if(point<(psu_arr[0].power*psu_arr[0].as)/psu_arr[0].price)
				{
					choose = psu_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
				}
			}
			result[6]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CASEDB.csv";//PSU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CASE 갯수
		CASE case_arr [] = new CASE[a];
		i=0;
		p=0;
		n=1;
		while(database.nextInt()!=0)
		{
			if(edge_case.equals(database.next())) {
				case_arr[i] =new CASE();
				if(input.cool==1)//cooling에 집중
				{
					case_arr[i].x = database.nextDouble();	
					case_arr[i].y = database.nextDouble();
					case_arr[i].h = database.nextDouble();
					database.nextDouble();
					case_arr[i].price = database.nextDouble();	
				}else//silence에 집중
				{
					database.nextDouble();
					database.nextDouble();
					database.nextDouble();
					case_arr[i].cooler=database.nextDouble();
					case_arr[i].price = database.nextDouble();
				}
				for(int tmp=0;tmp<6;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				case_arr[i].index=n;
				i++;
			}
			else
			{
				for(int tmp=0;tmp<11;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int case_arr_size = i;
		database.close();//case 노드 생성 끝
		if(case_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no CASE matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=0;
			choose=0;
			if(input.cool == 1) {
				point = (case_arr[0].x*case_arr[0].y*case_arr[0].h)/case_arr[0].price;
				for(int tmp=1; tmp<case_arr_size;tmp++)//GPU 선택
				{
					if(point<(case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h)/case_arr[tmp].price)
					{
						choose = case_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
					}
				}
			}else
			{
				point = case_arr[0].price/case_arr[0].cooler;
				for(int tmp=1; tmp<case_arr_size;tmp++)//GPU 선택
				{
					if(point<case_arr[tmp].price/case_arr[tmp].cooler)
					{
						choose = case_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
					}
				}
			}
			result[7]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CoolerDB.csv";//cooler 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CASE 갯수
		Cooler cooler_arr [] = new Cooler[a];
		i=0;
		p=0;
		n=1;
		while(database.nextInt()!=0)
		{
			if(input.cool == 1) {
				cooler_arr[i] =new Cooler();
				cooler_arr[i].air = database.nextDouble();
				cooler_arr[i].noisy=database.nextDouble();
				cooler_arr[i].price = database.nextDouble();
				for(int tmp=0;tmp<15;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				cooler_arr[i].index=n;
				i++;
			}
			else
			{
				for(int tmp=0;tmp<18;tmp++)
				{
					database.next();
				}
			}
			n++;
		}
		int cooler_arr_size = i;
		database.close();//case 노드 생성 끝
		if(input.cool==1) {
			if(cooler_arr_size == 0)//오류 검사
			{
				System.out.println("Wrong no Cooler matched CASE");
				scan.close();
				return;
			}
			else
			{
				double point=0;
				choose=0;
				point = cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);
				for(int tmp=1; tmp<cooler_arr_size;tmp++)//cooler 선택
				{
					if(point<cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price))
					{
						choose = cooler_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
					}
				}
		
				result[8]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			}
		}else
		{
			result[8]=0;
		}
		////////////////////////////////////////////////////////////////////////////////////////
		//드디어 출력부분
		//cpu
		path = "CPUDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[1]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int _tmp=0;_tmp<18;_tmp++) {
				database.next();
			}
		}
		System.out.println("(인덱스	밴치마크	가격	TDP(W)	오버클럭가능여부	소켓	CPU 품목	제품군	제조사	코어 수	쓰레드 수	동작속도	터보속도	L3캐시	내장 그래픽	GPU속도(Mhz)	최대 지원메모리(Mhz))");
		for(int tmp=0;tmp<18;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//gpu
		path = "GPUDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[0]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<13;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	밴치마크	가격	정격파워	제품	제조사	모델	코어클럭	부스트	팬 개수	메모리클럭	길이)");
		for(int tmp=0;tmp<13;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//MB
		path = "MBDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[2]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<17;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	소켓	단가	메모리 슬롯	램 클럭 지원	DDR버전	SATA3	M.2	WiFi	폼팩터	메인보드 품목	제조사	세부 칩셋	길이	폭	SLI)");
		for(int tmp=0;tmp<17;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//ram
		path = "RAMDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[3]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<14;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	용량(GB)	개수(개)	클럭(Mhz)	램 규격(DDR)	가격	RAM 품목	회사	타이밍	전압	XMP	색깔	LED)");
		for(int tmp=0;tmp<14;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//ssd
		path = "SSDDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[4]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<18;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	읽기 속도MB/s	쓰기 속도MB/s	A/S년	용량GB	가격	SSD 제품	저장 방식	컨트롤러	제조사	TRIM	GC	S.M.A.R.T	ECC	DEVSLP	SLC 캐싱	두께)");
		for(int tmp=0;tmp<18;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//hdd
		path = "HDDDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[5]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<11;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	용량TB	회전수RPM	가격	제품	제조사	모델명	버퍼용량	용량/플래터	인터페이스)");
		for(int tmp=0;tmp<11;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//psu
		path = "PSUDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[6]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<22;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	정격 용량W	무상 A/S 년	가격	제품	회사	레일	+12V 전류	4핀 IDE	SATA	PCI-E (6)	PCI-E(6+2)	Reform 케이블	모듈러	대기전력 1W	플랫 케이블	프리볼트	파워 규격	80 PLUS	액티브 PFC	깊이)");
		for(int tmp=0;tmp<22;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//case
		path = "CASEDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[7]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<13;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	크기	폭mm	높이mm	깊이mm	쿨러	가격	제품	측면	외부 색깔	내부 색깔	제조사)");
		for(int tmp=0;tmp<13;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//cooler
		path = "CoolerDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		database.next();
		p=result[8]-1;
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<19;tmp2++) {
				database.next();
			}
		}
		System.out.println("(인덱스	최대 풍량CFM	최대 소음 dBA	가격	System Cooler	제조사	팬 크기	두께		베어링	커넥터 핀	최소 회전	최대 회전	최소 소음	보증 기간	LED 색	발광 부분	날개 색	본체 색)");
		for(int tmp=0;tmp<19;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		database.close();
		System.out.println("");
	}
	
}