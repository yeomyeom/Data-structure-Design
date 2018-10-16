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
			input.cpu = 1000;
			input.gpu = 0;
			input.ram_vol = 4;
			input.cool = 1;//저소음 모드인 케이스를 고르다보니 가격이 말도 안되게 비싸진다.
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
			while(database.nextInt()!=0)
			{
				p=database.nextDouble();
				if(input.gpu<p)
				{
					gpu_arr[i]=new GPU();
					gpu_arr[i].point=p;//밴치점수
					gpu_arr[i].price=database.nextDouble();		//가격
					gpu_arr[i].tdp=database.nextDouble();		//소비전력
					gpu_arr[i].name=database.next();			//제품 명
				for(int tmp=0;tmp<8;tmp++)
				{
					database.next();
				}
				i++;
				}else
				{
					for(int tmp =0; tmp<11;tmp++)
					{
						database.next();
					}
				}
			}
			int gpu_arr_size = i;
			if(gpu_arr_size == 0)//오류 검사
			{
				System.out.println("Wrong GPU banchmark point");
				scan.close();
				return;
			}
			else
			{
				double point=gpu_arr[0].point/gpu_arr[0].price;
				for(int tmp=1; tmp<gpu_arr_size;tmp++)//GPU 선택
				{
					if(point<gpu_arr[tmp].point/gpu_arr[tmp].price)
					{	
						choose = tmp;
						sum = gpu_arr[tmp].price;
						edge_tdp = gpu_arr[tmp].tdp;
						point = gpu_arr[tmp].point/gpu_arr[tmp].price;
					}
				}
				sum_result = sum_result+sum;
				System.out.println("price now GPU: " +sum_result);
				System.out.println("GPU================");
				System.out.println("GPU 가격 " +gpu_arr[choose].price);
				System.out.println("GPU 정격파워(W) " +gpu_arr[choose].tdp);
				System.out.println("GPU 이름 " +gpu_arr[choose].name);
				System.out.println("GPU================");
				}
			database.close();//GPU 노드 생성 종료
		}
		////////////////////////////////////////////////////////////////////////
		path = "CPUDB.csv";//CPU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CPU 갯수
		CPU cpu_arr [] = new CPU[a];
		i=0;
		p=0.0;
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
				cpu_arr[i].name = database.next();
				for(int tmp=0;tmp<11;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				i++;
			}
			else
			{
				for(int tmp=0;tmp<16;tmp++)
				{
					database.next();
				}
			}
		}
		int cpu_arr_size = i;
		if(cpu_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong CPU banchmark point");
			scan.close();
			return;
		}
		else
		{
			double point=cpu_arr[0].point/cpu_arr[0].price;
			for(int tmp=1; tmp<cpu_arr_size;tmp++)//GPU 선택
			{
				if(point<cpu_arr[tmp].point/cpu_arr[tmp].price)
				{
					choose = tmp;
					sum = cpu_arr[tmp].price;
					edge = cpu_arr[tmp].soket;//CPU, MB간 엣지 연결
					overclock = cpu_arr[tmp].over;//오버클록 가능 여부
					edge_tdp_2=cpu_arr[tmp].tdp;
					point = cpu_arr[tmp].point/cpu_arr[tmp].price;
				}
			}
			sum_result = sum_result+sum;
			System.out.println("price now CPU: " +sum_result);
			System.out.println("CPU================");
			System.out.println("CPU 이름 " +cpu_arr[choose].name);
			System.out.println("CPU 가격 " +cpu_arr[choose].price);
			System.out.println("CPU 소비전력 " +cpu_arr[choose].tdp);
			System.out.println("CPU 오버클럭 여부 (Yes =1 No =0)" +cpu_arr[choose].over);
			System.out.println("CPU 소켓버전 " +cpu_arr[choose].soket);
			System.out.println("CPU================");
		}
		database.close();//CPU 노드 생성 끝
		/////////////////////////////////////////////////////////////////////////
		path="MBDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a=database.nextInt();
		MB mb_arr[]=new MB[a];//MB 노드 생성
		i=0;
		p=0;
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
				mb_arr[i].soket = s;
				mb_arr[i].memory_slot = database.nextInt();	//메모리 슬롯 갯수 MB RAM간 연결 접점
				mb_arr[i].ram_clock = database.nextDouble();	//램 클록 수 MB RAM간 연결 접점
				mb_arr[i].ddr_ver = database.nextInt();			//램 ddr 버전 MB RAM간 연결 접점
				mb_arr[i].paze=database.nextInt();
				mb_arr[i].chipset = database.next();			//MB와 CASE간 엣지 연결 조건
				mb_arr[i].name = database.next();
				for(int tmp=0;tmp<6;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				i++;
			}else
			{
				for(int tmp=0;tmp<13;tmp++)
				{
					database.next();
				}
			}
		}
		int mb_arr_size = i;
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
			for(int tmp=1; tmp<mb_arr_size;tmp++)//mb 선택
			{
				if(point<mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price)
				{
					choose = tmp;
					sum = mb_arr[tmp].price;
					edge_ram1 = mb_arr[tmp].memory_slot;//MB,ram간 엣지 연결
					edge_ram2 = mb_arr[tmp].ram_clock;//MB,ram간 엣지 연결
					edge_ram3 = mb_arr[tmp].ddr_ver;//MB,ram간 엣지 연결
					edge_case = mb_arr[tmp].chipset;//MB case간 엣지 연결
					point = mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price;
				}
			}
			sum_result = sum_result +sum;
			System.out.println("price now MB: " +sum_result);
			System.out.println("MB================");
			System.out.println("MB 이름 " +mb_arr[choose].name);
			System.out.println("MB 가격 " +mb_arr[choose].price);
			System.out.println("MB 소켓 " +mb_arr[choose].soket);
			System.out.println("MB 메모리 슬롯 갯수 " +mb_arr[choose].memory_slot);
			System.out.println("MB 램 지원 클럭 " +mb_arr[choose].ram_clock);
			System.out.println("MB 램 지원 버전(DDR4=4 DDR3=3) " +mb_arr[choose].ddr_ver);
			System.out.println("MB 전원부(페이즈) " +mb_arr[choose].paze);
			System.out.println("MB 칩셋 " +mb_arr[choose].chipset);
			System.out.println("MB================");
		}
		database.close();//MB 끝
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
		while(database.nextInt()!=0)
		{
			p=database.nextDouble();//용량
			q=database.nextInt();//갯수
			r=database.nextDouble();//클락
			l=database.nextInt();//ddr 버전
			if(input.ram_vol<=p && input.ram_vol*4>=p && edge_ram1 >= q && edge_ram2 <= r*2 && edge_ram3 == l)//Ram 용량이 사용자 입력값보다 크다면&& edge_ram2/2 <= r
			{
				ram_arr[i] =new RAM();
				ram_arr[i].vol = p;		
				ram_arr[i].ram_number = q;	//램갯수
				ram_arr[i].ram_clock = r;		//오버클럭 여부 메인보드 선택할때 참조함
				ram_arr[i].ddr_ver = l;			//소켓 버전 메인보드 선택할때 참조함
				ram_arr[i].price = database.nextDouble();	//가격
				ram_arr[i].name = database.next();
				for(int tmp=0;tmp<7;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				i++;
			}
			else
			{
				for(int tmp=0;tmp<9;tmp++)
				{
					database.next();
				}
			}
		}
		int ram_arr_size = i;
		if(ram_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no RAM matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=(ram_arr[0].vol*ram_arr[0].ram_clock/ram_arr[0].price);
			for(int tmp=1; tmp<ram_arr_size;tmp++)//ram 선택
			{
				if(point<(ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price)
				{
					choose = tmp;//램에서는 더이상 연결할 엣지가 없다.
					sum = ram_arr[tmp].price;
					point = ram_arr[tmp].vol*ram_arr[tmp].ram_clock/ram_arr[tmp].price;
				}
			}
			sum_result = sum_result +sum;
			System.out.println("price now ram: " +sum_result);
			System.out.println("RAM================");
			System.out.println("RAM 이름 " +ram_arr[choose].name);
			System.out.println("RAM 가격 " +ram_arr[choose].price);
			System.out.println("RAM 용량 " +ram_arr[choose].vol);
			System.out.println("RAM 갯수" +ram_arr[choose].ram_number);
			System.out.println("RAM 클락(MHz) " +ram_arr[choose].ram_clock);
			System.out.println("RAM 버전(DDR4=4, DDR3=3) " +ram_arr[choose].ddr_ver);
			System.out.println("RAM================");
		}
		database.close();//ram 노드 끝
		////////////////////////////////////////////////////////////////////////////////////////
		if(input.ssd_vol != 0) {
			path = "SSDDB.csv";//SSD 노드 생성 시작
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();// 총 SSD 갯수
			SSD ssd_arr [] = new SSD[a];
			i=0;
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
					ssd_arr[i].name = database.next();
					for(int tmp=0;tmp<11;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
					{
						database.next();
					}
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
			if(ssd_arr_size == 0)//오류 검사
			{
				System.out.println("Wrong no SSD matched MB");
				scan.close();
				return;
			}
			else
			{//ssd_arr[0].as*
				double point = (ssd_arr[0].rspeed+ssd_arr[0].wspeed)*ssd_arr[0].vol/ssd_arr[0].price;
				for(int tmp=1; tmp<ssd_arr_size;tmp++)//GPU 선택
				{
					if(point<(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)*ssd_arr[tmp].vol/ssd_arr[tmp].price)
					{
						choose = tmp;
						sum = ssd_arr[tmp].price;
						point = (ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)*ssd_arr[tmp].vol/ssd_arr[tmp].price;
					}
				}
				sum_result = sum_result +sum;
				System.out.println("price now ssd: " +sum_result);
				System.out.println("SSD================");
				System.out.println("SSD 이름 " +ssd_arr[choose].name);
				System.out.println("SSD 가격 " +ssd_arr[choose].price);
				System.out.println("SSD 용량 " +ssd_arr[choose].vol);
				System.out.println("SSD 읽기속도(MB/s) " +ssd_arr[choose].rspeed);
				System.out.println("SSD 쓰기속도(MB/s) " +ssd_arr[choose].wspeed);
				System.out.println("SDD AS기간 " +ssd_arr[choose].as);
				System.out.println("SSD================");
			}
			database.close();//SSD
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
			hdd_arr[i].name = database.next();
			for(int tmp=0;tmp<6;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
			{
				database.next();
			}
			i++;
		}
		int hdd_arr_size = i;
		if(hdd_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no HDD matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price;
			for(int tmp=1; tmp<hdd_arr_size;tmp++)//hdd 선택
			{
				if(point<hdd_arr[tmp].vol*hdd_arr[tmp].rpm/hdd_arr[tmp].price)
				{
					choose = tmp;
					sum = hdd_arr[tmp].price;
					point = hdd_arr[tmp].vol*hdd_arr[tmp].rpm/hdd_arr[tmp].price;
				}
			}
			sum_result = sum_result +sum;
			System.out.println("price now hdd: " +sum_result);
			System.out.println("HDD================");
			System.out.println("HDD 이름 " +hdd_arr[choose].name);
			System.out.println("HDD 가격 " +hdd_arr[choose].price);
			System.out.println("HDD 용량 " +hdd_arr[choose].vol);
			System.out.println("HDD 디스크 속도(rpm) " +hdd_arr[choose].rpm);
			System.out.println("HDD================");
		}
		database.close();//hdd 노드 끝
		////////////////////////////////////////////////////////////////////////////////////////
		path = "PSUDB.csv";//PSU 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 SSD 갯수
		PSU psu_arr [] = new PSU[a];
		i=0;
		p=0;
		double k=0.0;
		System.out.println("CPU " +edge_tdp_2+" GPU " +edge_tdp);
		if(edge_tdp != 0) {
			while(database.nextInt()!=0)
			{
				k=database.nextDouble();
				if((edge_tdp+edge_tdp_2)*1.2<=k)//정격용량이 CPU+GPU *1.2가 되야 안전하다
				{
					psu_arr[i] =new PSU();
					psu_arr[i].power = k;
					psu_arr[i].as = database.nextDouble();						
					psu_arr[i].price = database.nextDouble();
					psu_arr[i].name = database.next();
					for(int tmp=0;tmp<17;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
					{
						database.next();
					}
					i++;
				}
				else
				{
					for(int tmp=0;tmp<20;tmp++)
					{
						database.next();
					}
				}
			}
		}else
		{
			while(database.nextInt()!=0)
			{
				k=database.nextDouble();
				if(edge_tdp_2*5.0>=k)//cpu 하나만 고른경우
				{
					psu_arr[i] =new PSU();
					psu_arr[i].power = k;
					psu_arr[i].as = database.nextDouble();						
					psu_arr[i].price = database.nextDouble();
					psu_arr[i].name = database.next();
					for(int tmp=0;tmp<17;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
					{
						database.next();
					}
					i++;
				}
				else
				{
					for(int tmp=0;tmp<20;tmp++)
					{
						database.next();
					}
				}
			}
		}
		int psu_arr_size = i;
		if(psu_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no PSU matched CPU+GPU");
			scan.close();
			return;
		}
		else
		{
			double point=psu_arr[0].power*psu_arr[0].as/psu_arr[0].price;
			for(int tmp=1; tmp<psu_arr_size;tmp++)//GPU 선택
			{
				if(point<psu_arr[tmp].power*psu_arr[tmp].as/psu_arr[tmp].price)
				{
					choose = tmp;
					sum = psu_arr[tmp].price;
					point = psu_arr[tmp].power*psu_arr[tmp].as/psu_arr[tmp].price;
				}
			}
			sum_result = sum_result +sum;
			System.out.println("price now psu: " +sum_result);
			System.out.println("PSU================");
			System.out.println("PSU 이름 " +psu_arr[choose].name);
			System.out.println("PSU 가격 " +psu_arr[choose].price);
			System.out.println("PSU 정격용량 " +psu_arr[choose].power);
			System.out.println("PSU AS기간 " +psu_arr[choose].as);
			System.out.println("PSU================");
		}
		database.close();//psu 노드  끝
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CASEDB.csv";//case 노드 생성 시작
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// 총 CASE 갯수
		CASE case_arr [] = new CASE[a];
		i=0;
		p=0;
		while(database.nextInt()!=0)
		{
			if(edge_case.equals(database.next())) {
				case_arr[i] =new CASE();
				if(input.cool==1)//cooling에 집중
				{
					case_arr[i].x = database.nextDouble();	
					case_arr[i].y = database.nextDouble();
					case_arr[i].h = database.nextDouble();
					case_arr[i].cooler = database.nextDouble();
					case_arr[i].price = database.nextDouble();	
					case_arr[i].name = database.next();
				}else//silence에 집중
				{
					database.nextDouble();
					database.nextDouble();
					database.nextDouble();
					case_arr[i].cooler=database.nextDouble();
					case_arr[i].price = database.nextDouble();
					case_arr[i].name = database.next();
				}
				for(int tmp=0;tmp<5;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				i++;
			}
			else
			{
				for(int tmp=0;tmp<11;tmp++)
				{
					database.next();
				}
			}
		}
		int case_arr_size = i;
		if(case_arr_size == 0)//오류 검사
		{
			System.out.println("Wrong no CASE matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=0.0;
			if(input.cool == 1) {
				point = case_arr[0].x*case_arr[0].y*case_arr[0].h*case_arr[0].cooler/case_arr[0].price;
				for(int tmp=1; tmp<case_arr_size;tmp++)//case 선택
				{
					if(point<case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h*case_arr[tmp].cooler/case_arr[tmp].price)
					{
						choose = tmp;
						sum = case_arr[tmp].price;
						point = case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h*case_arr[tmp].cooler/case_arr[tmp].price;
					}
				}
				sum_result = sum_result +sum;
				System.out.println("price now case: " +sum_result);
				System.out.println("CASE================");
				System.out.println("case를 cooling 우선으로 선택하였습니다.");
				System.out.println("case 이름 " +case_arr[choose].name);
				System.out.println("case 가격 " +case_arr[choose].price);
				System.out.println("case 가로" +case_arr[choose].x);
				System.out.println("case 세로" +case_arr[choose].y);
				System.out.println("case 높이" +case_arr[choose].h);
				System.out.println("case 쿨러 갯수" +case_arr[choose].cooler);
				System.out.println("CASE================");
				
			}else
			{
				point = case_arr[0].price/case_arr[0].cooler;
				for(int tmp=1; tmp<case_arr_size;tmp++)//GPU 선택
				{
					if(point<case_arr[tmp].price/case_arr[tmp].cooler)
					{
						choose = tmp;//램에서는 더이상 연결할 엣지가 없다.
						sum = case_arr[tmp].price;
						point = case_arr[tmp].price/case_arr[tmp].cooler;
					}
				}
				sum_result = sum_result +sum;
				System.out.println("price now case: " +sum_result);
				System.out.println("CASE================");
				System.out.println("case를 저소음 우선으로 선택하였습니다.");
				System.out.println("case 이름 " +case_arr[choose].name);
				System.out.println("case 가격 " +case_arr[choose].price);
				System.out.println("case 쿨러 갯수" +case_arr[choose].cooler);
				System.out.println("CASE================");
			}
		}
		database.close();//case 노드 끝
		////////////////////////////////////////////////////////////////////////////////////////
		if(input.cool == 1) {
			path = "CoolerDB.csv";//cooler 노드 생성 시작
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();// 총 cooler갯수
			Cooler cooler_arr [] = new Cooler[a];
			i=0;
			p=0;
			while(database.nextInt()!=0)
			{
	
				cooler_arr[i] =new Cooler();
				cooler_arr[i].air = database.nextDouble();
				cooler_arr[i].noisy=database.nextDouble();
				cooler_arr[i].price = database.nextDouble();
				cooler_arr[i].name = database.next();
				for(int tmp=0;tmp<14;tmp++)					//제품 고를때 상관 없는 정보들 + /n pass
				{
					database.next();
				}
				i++;
			}
			int cooler_arr_size = i;
			if(cooler_arr_size == 0)//오류 검사
			{
				System.out.println("Wrong no Cooler matched CASE");
				scan.close();
				return;
			}
			else
			{
				double point=cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);;
				for(int tmp=1; tmp<cooler_arr_size;tmp++)//cooler 선택
				{
					if(point<cooler_arr[tmp].air/(cooler_arr[tmp].noisy*cooler_arr[tmp].price))
					{
						choose = tmp;//cooler에서는 더이상 연결할 엣지가 없다.
						sum = cooler_arr[tmp].price;
						point = cooler_arr[tmp].air/(cooler_arr[tmp].noisy*cooler_arr[tmp].price);
					}
				}
				sum_result = sum_result +sum;
				System.out.println("price now cooler: " +sum_result);
				System.out.println("Cooler================");
				System.out.println("case를 cooling 우선으로 선택하였습니다.");
				System.out.println("cooler 이름 " +cooler_arr[choose].name);
				System.out.println("cooler 가격 " +cooler_arr[choose].price);
				System.out.println("cooler 소음(dBA) " +cooler_arr[choose].noisy);
				System.out.println("cooler 풍량 " +cooler_arr[choose].air);
				System.out.println("Cooler================");
			}
			database.close();//cooler 노드 끝
		}
	////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("총 가격 : "+sum_result);
	}
}