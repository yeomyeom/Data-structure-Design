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
		double sum=0.0;
		double sum_result=0.0;
		//user input
		System.out.println("맞추고 싶은 PC 성능을 고르시오 : ");
		System.out.println("1. 배틀그라운드");
		System.out.println("2. CS GO");
		System.out.println("3. 문명 6");
		System.out.println("4. 레보식");
		System.out.println("5. 효도컴");
		System.out.println("기타. 커스텀");
		choose = scan.nextInt();
		switch (choose)
		{
		case 1:
			input.cpu = 8050;
			input.gpu = 8900;
			input.ram_vol = 16;
			input.cool = 1;
			input.ssd_vol = 250;
			break;
		case 2:
			input.cpu = 1800;
			input.gpu = 1000;
			input.ram_vol = 2;
			input.cool = 0;
			input.ssd_vol = 250;
			break;
		case 3:
			input.cpu = 11500;
			input.gpu = 6000;
			input.ram_vol = 16;
			input.cool = 1;
			input.ssd_vol = 250;
			break;
		case 4:
			input.cpu = 6400;
			input.gpu = 5700;
			input.ram_vol = 8;
			input.cool = 1;
			input.ssd_vol = 250;
			break;
		case 5:
			input.cpu = 3000;
			input.gpu = 0;
			input.ram_vol = 4;
			input.cool = 0;
			input.ssd_vol = 0;
			break;
		default:
			System.out.print("CPU banchmark point : ");
			input.cpu = scan.nextDouble();
			System.out.print("GPU banchmark point : ");
			input.gpu = scan.nextDouble();
			System.out.print("Ram volume : ");
			input.ram_vol = scan.nextDouble();
			System.out.print("Cooling or silence : ");
			input.cool=scan.nextInt();
			System.out.print("SSD maximum vol (In normal 250 is better than anyother: ");
			input.ssd_vol=scan.nextDouble();
		}
		scan.close();
		Scanner database;
		int i;
		int n;
		int a;
		double p;
		///////////////////////////////////////////////////////////////////////
		if(input.gpu != 0) {
			path = "GPUDB.csv";//GPU 노드 생성 시작
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();
			GPU gpu_arr[] = new GPU[a];
			i=0;
			p=0.0;
			n=1;
			while(database.nextInt()!=0)
			{
				p=database.nextDouble();
				if(input.gpu<p)
				{
					gpu_arr[i]=new GPU();
					gpu_arr[i].point=p;//밴치점수
					gpu_arr[i].price=database.nextDouble();	
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
				
				double point=gpu_arr[0].point/gpu_arr[0].price;
				choose=gpu_arr[0].index;
				for(int tmp=1; tmp<gpu_arr_size;tmp++)//GPU 선택
				{
					if(point<gpu_arr[tmp].point/gpu_arr[tmp].price)
					{	
						choose = gpu_arr[tmp].index;
						sum = gpu_arr[tmp].price;
						edge_tdp = gpu_arr[tmp].tdp;
						point = gpu_arr[tmp].point/gpu_arr[tmp].price;
					}
					}
					result[0]=choose;//최종 결과물 출력때 GPUDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
					sum_result = sum_result+sum;
					System.out.println("price now GPU: " +sum_result);
				}
		}else
		{
			
		}
		////////////////////////////////////////////////////////////////////////
		path = "CPUDB.csv";//CPU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CPU 갯수
		CPU cpu_arr [] = new CPU[a];
		i=0;
		p=0.0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextDouble();
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
			choose=cpu_arr[0].index;
			for(int tmp=1; tmp<cpu_arr_size;tmp++)//GPU 선택
			{
				if(point<cpu_arr[tmp].point/cpu_arr[tmp].price)
				{
					choose = cpu_arr[tmp].index;
					sum = cpu_arr[tmp].price;
					edge = cpu_arr[tmp].soket;//CPU, MB간 엣지 연결
					overclock = cpu_arr[tmp].over;//오버클록 가능 여부
					edge_tdp_2=cpu_arr[tmp].tdp;
					point = cpu_arr[tmp].point/cpu_arr[tmp].price;
				}
			}
			result[1]=choose;//최종 결과물 출력때 CPUDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			sum_result = sum_result+sum;
			System.out.println("price now CPU: " +sum_result);
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
		String s;
		int overMB;
		
		while(database.nextInt()!=0)
		{
			 s = database.next();
			 overMB = database.nextInt();
			if(edge.equals(s) && overMB == overclock)//CPU, MB 엣지 연결
			{
				mb_arr[i] =new MB();
				mb_arr[i].price = database.nextDouble();			//가격
				mb_arr[i].memory_slot = database.nextInt();	//메모리 슬롯 갯수 MB RAM간 연결 접점
				mb_arr[i].ram_clock = database.nextDouble();	//램 클록 수 MB RAM간 연결 접점
				mb_arr[i].ddr_ver = database.nextInt();			//램 ddr 버전 MB RAM간 연결 접점
				database.next();			//sata 3가 몇개 연결 가능한가 MB HDDSSD간 연결 접점
				mb_arr[i].paze=database.nextInt();
				mb_arr[i].chipset = database.next();			//MB와 CASE간 엣지 연결 조건
				for(int tmp=0;tmp<7;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				mb_arr[i].index=n;
				i++;
			}else
			{
				for(int tmp=0;tmp<14;tmp++)
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
			double point=(mb_arr[0].paze*mb_arr[0].ram_clock/mb_arr[0].price);
			choose=mb_arr[0].index;
			for(int tmp=1; tmp<mb_arr_size;tmp++)//GPU 선택
			{
				if(point<mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price)
				{
					choose = mb_arr[tmp].index;
					sum = mb_arr[tmp].price;
					edge_ram1 = mb_arr[tmp].memory_slot;//MB,ram간 엣지 연결
					edge_ram2 = mb_arr[tmp].ram_clock;//MB,ram간 엣지 연결
					edge_ram3 = mb_arr[tmp].ddr_ver;//MB,ram간 엣지 연결
					edge_case = mb_arr[tmp].chipset;//MB case간 엣지 연결
					point = mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price;
				}
			}
			result[2]=choose;//최종 결과물 출력때 MBDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			sum_result = sum_result +sum;
			System.out.println("price now MB: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "RAMDB.csv";//ram 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 ram 갯수
		RAM ram_arr [] = new RAM[a];
		i=0;
		p=0;
		int q=0;
		double r=0.0;
		int l=0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextDouble();//용량
			q=database.nextInt();//갯수
			r=database.nextDouble();//클락
			l=database.nextInt();//ddr 버전
			if(input.ram_vol<=p &&input.ram_vol*2>=p && edge_ram1 >= q && edge_ram2/2 <= r && edge_ram3 == l)//Ram 용량이 사용자 입력값보다 크다면&& edge_ram2/2 <= r
			{
				ram_arr[i] =new RAM();
				ram_arr[i].vol = p;		
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
			choose=ram_arr[0].index;
			for(int tmp=1; tmp<ram_arr_size;tmp++)//GPU 선택
			{
				if(point<(ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price)
				{
					choose = ram_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
					sum = ram_arr[tmp].price;
					point = (ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price;
				}
			}
			result[3]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			sum_result = sum_result +sum;
			System.out.println("price now ram: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		if(input.ssd_vol != 0) {
			path = "SSDDB.csv";//SSD 노드 생성 시작
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();// 총 SSD 갯수
			SSD ssd_arr [] = new SSD[a];
			i=0;
			n=0;
			double vol=0.0;
			p=0;
			while(database.nextInt()!=0)
			{
				vol = database.nextDouble();
				if(input.ssd_vol >= vol) {
					ssd_arr[i] =new SSD();
					ssd_arr[i].vol = vol;
					ssd_arr[i].rspeed = database.nextDouble();	//읽기속도
					ssd_arr[i].wspeed = database.nextDouble();	//쓰기 속도
					ssd_arr[i].as = database.nextDouble();		//as기간
					ssd_arr[i].price = database.nextDouble();   //가격
					for(int tmp=0;tmp<12;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
					{
						database.next();
					}
					ssd_arr[i].index=i+1;
					i++;
				}
				else
				{
					for(int tmp =0; tmp<16 ; tmp++) {
						database.next();
					}
				}
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
				double point = ssd_arr[0].as*ssd_arr[0].vol*(ssd_arr[0].rspeed+ssd_arr[0].wspeed)/ssd_arr[0].price;
				choose=ssd_arr[0].index;
				for(int tmp=1; tmp<ssd_arr_size;tmp++)//GPU 선택
				{
					if(point<ssd_arr[tmp].as*ssd_arr[tmp].vol*(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)/ssd_arr[tmp].price)
					{
						choose = ssd_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
						sum = ssd_arr[tmp].price;
						point = ssd_arr[tmp].as*ssd_arr[tmp].vol*(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)/ssd_arr[tmp].price;
					}
				}
				result[4]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
				sum_result = sum_result +sum;
				System.out.println("price now ssd: " +sum_result);
			}
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
			choose=hdd_arr[0].index;
			for(int tmp=1; tmp<hdd_arr_size;tmp++)//GPU 선택
			{
				if(point<hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price)
				{
					choose = hdd_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
					sum = hdd_arr[tmp].price;
					point = hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price;
				}
			}
			result[5]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			sum_result = sum_result +sum;
			System.out.println("price now hdd: " +sum_result);
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
			if((edge_tdp+edge_tdp_2)*1.1<k)//정격용량이 CPU+GPU *1.2가 되야 안전하다
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
			choose=psu_arr[0].index;
			for(int tmp=1; tmp<psu_arr_size;tmp++)//GPU 선택
			{
				if(point<psu_arr[0].power*psu_arr[0].as/psu_arr[0].price)
				{
					choose = psu_arr[tmp].index;//램에서는 더이상 연결할 엣지가 없다.
					sum = psu_arr[tmp].price;
					point = psu_arr[0].power*psu_arr[0].as/psu_arr[0].price;
				}
			}
			result[6]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			sum_result = sum_result +sum;
			System.out.println("price now psu: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CASEDB.csv";//case 노드 생성 시작
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
			double point=0.0;
			choose=case_arr[0].index;
			if(input.cool == 1) {
				point = case_arr[0].x*case_arr[0].y*case_arr[0].h/case_arr[0].price;
				for(int tmp=1; tmp<case_arr_size;tmp++)//case 선택
				{
					if(point<case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h/case_arr[tmp].price)
					{
						choose = case_arr[tmp].index;//case에서는 더이상 연결할 엣지가 없다.
						sum = case_arr[tmp].price;
						point = case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h/case_arr[tmp].price;
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
						sum = case_arr[tmp].price;
						point = case_arr[tmp].price/case_arr[tmp].cooler;
					}
				}
			}
			result[7]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
			sum_result = sum_result +sum;
			System.out.println("price now case: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CoolerDB.csv";//cooler 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 cooler갯수
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
		database.close();//cooler 노드 생성 끝
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
				choose=cooler_arr[0].index;
				point = cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);
				for(int tmp=1; tmp<cooler_arr_size;tmp++)//cooler 선택
				{
					if(point<cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price))
					{
						choose = cooler_arr[tmp].index;//cooler에서는 더이상 연결할 엣지가 없다.
						sum = cooler_arr[tmp].price;
						point = cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);
					}
				}
				result[8]=choose;//최종 결과물 출력때 RAMDB에 choose 인덱스 값을 가지고 있는 제품이 출력됨
				sum_result = sum_result +sum;
				System.out.println("price now cooler: " +sum_result);
			}
		}else
		{
			result[8]=0;
		}
		////////////////////////////////////////////////////////////////////////////////////////
		//드디어 출력부분
		System.out.println("총 가격 : "+sum_result);
		//gpu
		if(input.gpu != 0) {
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
			System.out.println("(GPU 인덱스	밴치마크	가격	정격파워	제품	제조사	모델	코어클럭	부스트	팬 개수	메모리클럭	길이)");
			for(int tmp=0;tmp<13;tmp++)
			{
				System.out.print(database.next()+", ");
			}
			System.out.println("");
			database.close();
			}
		
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
		System.out.println("(CPU 인덱스	밴치마크	가격	TDP(W)	오버클럭가능여부	소켓	CPU 품목	제품군	제조사	코어 수	쓰레드 수	동작속도	터보속도	L3캐시	내장 그래픽	GPU속도(Mhz)	최대 지원메모리(Mhz))");
		for(int tmp=0;tmp<18;tmp++)
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
		System.out.println("(MB 소켓 	오버클럭가능여부 	단가	메모리 슬롯	램 클럭 지원	DDR버전	SATA3	페이즈 수	폼팩터	메인보드 품목	제조사	세부 칩셋	Wifi 가로 세로)");
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
		System.out.println("(RAM 인덱스	용량(GB)	개수(개)	클럭(Mhz)	램 규격(DDR)	가격	RAM 품목	회사	타이밍	전압	XMP	색깔	LED)");
		for(int tmp=0;tmp<14;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//ssd
		if(input.ssd_vol != 0) {
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
			System.out.println("(SSD 인덱스 	용량GB	읽기 속도MB/s	쓰기 속도MB/s	A/S년	가격	SSD 제품	저장 방식	컨트롤러	제조사	TRIM	GC	S.M.A.R.T	ECC	DEVSLP	SLC 캐싱	두께)");
			for(int tmp=0;tmp<18;tmp++)
			{
				System.out.print(database.next()+", ");
			}
			System.out.println("");
			database.close();
		}
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
		System.out.println("(HDD인덱스	용량TB	회전수RPM	가격	제품	제조사	모델명	버퍼용량	용량/플래터	인터페이스)");
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
		System.out.println("(PSU 인덱스	정격 용량W	무상 A/S 년	가격	제품	회사	레일	+12V 전류	4핀 IDE	SATA	PCI-E (6)	PCI-E(6+2)	Reform 케이블	모듈러	대기전력 1W	플랫 케이블	프리볼트	파워 규격	80 PLUS	액티브 PFC	깊이)");
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
		p=result[7];
		for(int tmp=0;tmp<p;tmp++)
		{
			for(int tmp2=0;tmp2<13;tmp2++) {
				database.next();
			}
		}
		System.out.println("(CASE 인덱스	크기	폭mm	높이mm	깊이mm	쿨러	가격	제품	측면	외부 색깔	내부 색깔	제조사)");
		for(int tmp=0;tmp<13;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		System.out.println("");
		database.close();
		
		//cooler
		path = "CoolerDB.csv";
		if(input.cool == 1) {
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
			System.out.println("(cooler 인덱스	최대 풍량CFM	최대 소음 dBA	가격	System Cooler	제조사	팬 크기	두께		베어링	커넥터 핀	최소 회전	최대 회전	최소 소음	보증 기간	LED 색	발광 부분	날개 색	본체 색)");
			for(int tmp=0;tmp<19;tmp++)
			{
				System.out.print(database.next()+", ");
			}
			database.close();
		}
		else
		{
			System.out.println("NO Cooler");
		}
		System.out.println("");
	}
	
}