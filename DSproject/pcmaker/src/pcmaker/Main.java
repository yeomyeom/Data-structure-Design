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
		System.out.println("���߰� ���� PC ������ ���ÿ� : ");
		System.out.println("1. ��Ʋ�׶���");
		System.out.println("2. CS GO");
		System.out.println("3. ���� 6");
		System.out.println("4. ������");
		System.out.println("5. ȿ����");
		System.out.println("��Ÿ. Ŀ����");
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
			path = "GPUDB.csv";//GPU ��� ���� ����
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
					gpu_arr[i].point=p;//��ġ����
					gpu_arr[i].price=database.nextDouble();	
					gpu_arr[i].tdp=database.nextDouble();		//�Һ�����
				for(int tmp=0;tmp<9;tmp++)
				{
					database.next();
				}
				gpu_arr[i].index=n;//���߿� DB���� ã�� �ε��� ��
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
			database.close();//GPU ��� ���� ����
			if(gpu_arr_size == 0)//���� �˻�
			{
				System.out.println("Wrong GPU banchmark point");
				scan.close();
				return;
			}
			else
			{
				
				double point=gpu_arr[0].point/gpu_arr[0].price;
				choose=gpu_arr[0].index;
				for(int tmp=1; tmp<gpu_arr_size;tmp++)//GPU ����
				{
					if(point<gpu_arr[tmp].point/gpu_arr[tmp].price)
					{	
						choose = gpu_arr[tmp].index;
						sum = gpu_arr[tmp].price;
						edge_tdp = gpu_arr[tmp].tdp;
						point = gpu_arr[tmp].point/gpu_arr[tmp].price;
					}
					}
					result[0]=choose;//���� ����� ��¶� GPUDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
					sum_result = sum_result+sum;
					System.out.println("price now GPU: " +sum_result);
				}
		}else
		{
			
		}
		////////////////////////////////////////////////////////////////////////
		path = "CPUDB.csv";//CPU ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CPU ����
		CPU cpu_arr [] = new CPU[a];
		i=0;
		p=0.0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextDouble();
			if(input.cpu<p)//cpu ��ġ������ ����� �Է°����� ũ�ٸ�
			{
				cpu_arr[i] =new CPU();
				cpu_arr[i].point = p;						//��ġ����
				cpu_arr[i].price = database.nextDouble();	//����
				cpu_arr[i].tdp = database.nextDouble();		//��� ���� �Ŀ� ���� ������
				cpu_arr[i].over = database.nextInt();		//����Ŭ�� ���� ���κ��� �����Ҷ� ������
				cpu_arr[i].soket = database.next();			//���� ���� ���κ��� �����Ҷ� ������
				for(int tmp=0;tmp<12;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		database.close();//CPU ��� ���� ��
		if(cpu_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong CPU banchmark point");
			scan.close();
			return;
		}
		else
		{
			double point=(cpu_arr[0].point/cpu_arr[0].price);
			choose=cpu_arr[0].index;
			for(int tmp=1; tmp<cpu_arr_size;tmp++)//GPU ����
			{
				if(point<cpu_arr[tmp].point/cpu_arr[tmp].price)
				{
					choose = cpu_arr[tmp].index;
					sum = cpu_arr[tmp].price;
					edge = cpu_arr[tmp].soket;//CPU, MB�� ���� ����
					overclock = cpu_arr[tmp].over;//����Ŭ�� ���� ����
					edge_tdp_2=cpu_arr[tmp].tdp;
					point = cpu_arr[tmp].point/cpu_arr[tmp].price;
				}
			}
			result[1]=choose;//���� ����� ��¶� CPUDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			sum_result = sum_result+sum;
			System.out.println("price now CPU: " +sum_result);
		}
		/////////////////////////////////////////////////////////////////////////
		path="MBDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a=database.nextInt();
		MB mb_arr[]=new MB[a];//MB ��� ����
		i=0;
		p=0;
		n=1;
		String s;
		int overMB;
		
		while(database.nextInt()!=0)
		{
			 s = database.next();
			 overMB = database.nextInt();
			if(edge.equals(s) && overMB == overclock)//CPU, MB ���� ����
			{
				mb_arr[i] =new MB();
				mb_arr[i].price = database.nextDouble();			//����
				mb_arr[i].memory_slot = database.nextInt();	//�޸� ���� ���� MB RAM�� ���� ����
				mb_arr[i].ram_clock = database.nextDouble();	//�� Ŭ�� �� MB RAM�� ���� ����
				mb_arr[i].ddr_ver = database.nextInt();			//�� ddr ���� MB RAM�� ���� ����
				database.next();			//sata 3�� � ���� �����Ѱ� MB HDDSSD�� ���� ����
				mb_arr[i].paze=database.nextInt();
				mb_arr[i].chipset = database.next();			//MB�� CASE�� ���� ���� ����
				for(int tmp=0;tmp<7;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(mb_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no MB soket matched CPU");
			scan.close();
			return;
		}
		else
		{
			double point=(mb_arr[0].paze*mb_arr[0].ram_clock/mb_arr[0].price);
			choose=mb_arr[0].index;
			for(int tmp=1; tmp<mb_arr_size;tmp++)//GPU ����
			{
				if(point<mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price)
				{
					choose = mb_arr[tmp].index;
					sum = mb_arr[tmp].price;
					edge_ram1 = mb_arr[tmp].memory_slot;//MB,ram�� ���� ����
					edge_ram2 = mb_arr[tmp].ram_clock;//MB,ram�� ���� ����
					edge_ram3 = mb_arr[tmp].ddr_ver;//MB,ram�� ���� ����
					edge_case = mb_arr[tmp].chipset;//MB case�� ���� ����
					point = mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price;
				}
			}
			result[2]=choose;//���� ����� ��¶� MBDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			sum_result = sum_result +sum;
			System.out.println("price now MB: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "RAMDB.csv";//ram ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� ram ����
		RAM ram_arr [] = new RAM[a];
		i=0;
		p=0;
		int q=0;
		double r=0.0;
		int l=0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextDouble();//�뷮
			q=database.nextInt();//����
			r=database.nextDouble();//Ŭ��
			l=database.nextInt();//ddr ����
			if(input.ram_vol<=p &&input.ram_vol*2>=p && edge_ram1 >= q && edge_ram2/2 <= r && edge_ram3 == l)//Ram �뷮�� ����� �Է°����� ũ�ٸ�&& edge_ram2/2 <= r
			{
				ram_arr[i] =new RAM();
				ram_arr[i].vol = p;		
				ram_arr[i].ram_number = q;	//������
				ram_arr[i].ram_clock = r;		//����Ŭ�� ���� ���κ��� �����Ҷ� ������
				ram_arr[i].ddr_ver = l;			//���� ���� ���κ��� �����Ҷ� ������
				ram_arr[i].price = database.nextDouble();	//����
				for(int tmp=0;tmp<8;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		database.close();//CPU ��� ���� ��
		if(ram_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no RAM matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=((ram_arr[0].vol*ram_arr[0].ram_clock)/ram_arr[0].price);
			choose=ram_arr[0].index;
			for(int tmp=1; tmp<ram_arr_size;tmp++)//GPU ����
			{
				if(point<(ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price)
				{
					choose = ram_arr[tmp].index;//�������� ���̻� ������ ������ ����.
					sum = ram_arr[tmp].price;
					point = (ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price;
				}
			}
			result[3]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			sum_result = sum_result +sum;
			System.out.println("price now ram: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		if(input.ssd_vol != 0) {
			path = "SSDDB.csv";//SSD ��� ���� ����
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();// �� SSD ����
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
					ssd_arr[i].rspeed = database.nextDouble();	//�б�ӵ�
					ssd_arr[i].wspeed = database.nextDouble();	//���� �ӵ�
					ssd_arr[i].as = database.nextDouble();		//as�Ⱓ
					ssd_arr[i].price = database.nextDouble();   //����
					for(int tmp=0;tmp<12;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
			database.close();//CPU ��� ���� ��
			if(ssd_arr_size == 0)//���� �˻�
			{
				System.out.println("Wrong no SSD matched MB");
				scan.close();
				return;
			}
			else
			{
				double point = ssd_arr[0].as*ssd_arr[0].vol*(ssd_arr[0].rspeed+ssd_arr[0].wspeed)/ssd_arr[0].price;
				choose=ssd_arr[0].index;
				for(int tmp=1; tmp<ssd_arr_size;tmp++)//GPU ����
				{
					if(point<ssd_arr[tmp].as*ssd_arr[tmp].vol*(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)/ssd_arr[tmp].price)
					{
						choose = ssd_arr[tmp].index;//�������� ���̻� ������ ������ ����.
						sum = ssd_arr[tmp].price;
						point = ssd_arr[tmp].as*ssd_arr[tmp].vol*(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)/ssd_arr[tmp].price;
					}
				}
				result[4]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
				sum_result = sum_result +sum;
				System.out.println("price now ssd: " +sum_result);
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "HDDDB.csv";//HDD ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� HDD ����
		HDD hdd_arr [] = new HDD[a];
		i=0;
		p=0;
		while(database.nextInt()!=0)
		{
			hdd_arr[i] =new HDD();
			hdd_arr[i].vol = database.nextDouble();	//�뷮
			hdd_arr[i].rpm = database.nextDouble();	//���� �ӵ�
			hdd_arr[i].price = database.nextDouble();   //����
			for(int tmp=0;tmp<7;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
			{
				database.next();
			}
			hdd_arr[i].index=i+1;
			i++;
		}
		int hdd_arr_size = i;
		database.close();//CPU ��� ���� ��
		if(hdd_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no HDD matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price;
			choose=hdd_arr[0].index;
			for(int tmp=1; tmp<hdd_arr_size;tmp++)//GPU ����
			{
				if(point<hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price)
				{
					choose = hdd_arr[tmp].index;//�������� ���̻� ������ ������ ����.
					sum = hdd_arr[tmp].price;
					point = hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price;
				}
			}
			result[5]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			sum_result = sum_result +sum;
			System.out.println("price now hdd: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "PSUDB.csv";//PSU ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� SSD ����
		PSU psu_arr [] = new PSU[a];
		i=0;
		p=0;
		n=1;
		double k=0.0;
		while(database.nextInt()!=0)
		{
			k=database.nextDouble();
			if((edge_tdp+edge_tdp_2)*1.1<k)//���ݿ뷮�� CPU+GPU *1.2�� �Ǿ� �����ϴ�
			{
				psu_arr[i] =new PSU();
				psu_arr[i].power = k;
				psu_arr[i].as = database.nextDouble();						
				psu_arr[i].price = database.nextDouble();	
				for(int tmp=0;tmp<18;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		database.close();//psu ��� ���� ��
		if(psu_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no PSU matched CPU+GPU");
			scan.close();
			return;
		}
		else
		{
			double point=(psu_arr[0].power*psu_arr[0].as)/psu_arr[0].price;
			choose=psu_arr[0].index;
			for(int tmp=1; tmp<psu_arr_size;tmp++)//GPU ����
			{
				if(point<psu_arr[0].power*psu_arr[0].as/psu_arr[0].price)
				{
					choose = psu_arr[tmp].index;//�������� ���̻� ������ ������ ����.
					sum = psu_arr[tmp].price;
					point = psu_arr[0].power*psu_arr[0].as/psu_arr[0].price;
				}
			}
			result[6]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			sum_result = sum_result +sum;
			System.out.println("price now psu: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CASEDB.csv";//case ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CASE ����
		CASE case_arr [] = new CASE[a];
		i=0;
		p=0;
		n=1;
		while(database.nextInt()!=0)
		{
			if(edge_case.equals(database.next())) {
				case_arr[i] =new CASE();
				if(input.cool==1)//cooling�� ����
				{
					case_arr[i].x = database.nextDouble();	
					case_arr[i].y = database.nextDouble();
					case_arr[i].h = database.nextDouble();
					database.nextDouble();
					case_arr[i].price = database.nextDouble();	
				}else//silence�� ����
				{
					database.nextDouble();
					database.nextDouble();
					database.nextDouble();
					case_arr[i].cooler=database.nextDouble();
					case_arr[i].price = database.nextDouble();
				}
				for(int tmp=0;tmp<6;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		database.close();//case ��� ���� ��
		if(case_arr_size == 0)//���� �˻�
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
				for(int tmp=1; tmp<case_arr_size;tmp++)//case ����
				{
					if(point<case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h/case_arr[tmp].price)
					{
						choose = case_arr[tmp].index;//case������ ���̻� ������ ������ ����.
						sum = case_arr[tmp].price;
						point = case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h/case_arr[tmp].price;
					}
				}
			}else
			{
				point = case_arr[0].price/case_arr[0].cooler;
				for(int tmp=1; tmp<case_arr_size;tmp++)//GPU ����
				{
					if(point<case_arr[tmp].price/case_arr[tmp].cooler)
					{
						choose = case_arr[tmp].index;//�������� ���̻� ������ ������ ����.
						sum = case_arr[tmp].price;
						point = case_arr[tmp].price/case_arr[tmp].cooler;
					}
				}
			}
			result[7]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			sum_result = sum_result +sum;
			System.out.println("price now case: " +sum_result);
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CoolerDB.csv";//cooler ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� cooler����
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
				for(int tmp=0;tmp<15;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		database.close();//cooler ��� ���� ��
		if(input.cool==1) {
			if(cooler_arr_size == 0)//���� �˻�
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
				for(int tmp=1; tmp<cooler_arr_size;tmp++)//cooler ����
				{
					if(point<cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price))
					{
						choose = cooler_arr[tmp].index;//cooler������ ���̻� ������ ������ ����.
						sum = cooler_arr[tmp].price;
						point = cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);
					}
				}
				result[8]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
				sum_result = sum_result +sum;
				System.out.println("price now cooler: " +sum_result);
			}
		}else
		{
			result[8]=0;
		}
		////////////////////////////////////////////////////////////////////////////////////////
		//���� ��ºκ�
		System.out.println("�� ���� : "+sum_result);
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
			System.out.println("(GPU �ε���	��ġ��ũ	����	�����Ŀ�	��ǰ	������	��	�ھ�Ŭ��	�ν�Ʈ	�� ����	�޸�Ŭ��	����)");
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
		System.out.println("(CPU �ε���	��ġ��ũ	����	TDP(W)	����Ŭ�����ɿ���	����	CPU ǰ��	��ǰ��	������	�ھ� ��	������ ��	���ۼӵ�	�ͺ��ӵ�	L3ĳ��	���� �׷���	GPU�ӵ�(Mhz)	�ִ� �����޸�(Mhz))");
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
		System.out.println("(MB ���� 	����Ŭ�����ɿ��� 	�ܰ�	�޸� ����	�� Ŭ�� ����	DDR����	SATA3	������ ��	������	���κ��� ǰ��	������	���� Ĩ��	Wifi ���� ����)");
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
		System.out.println("(RAM �ε���	�뷮(GB)	����(��)	Ŭ��(Mhz)	�� �԰�(DDR)	����	RAM ǰ��	ȸ��	Ÿ�̹�	����	XMP	����	LED)");
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
			System.out.println("(SSD �ε��� 	�뷮GB	�б� �ӵ�MB/s	���� �ӵ�MB/s	A/S��	����	SSD ��ǰ	���� ���	��Ʈ�ѷ�	������	TRIM	GC	S.M.A.R.T	ECC	DEVSLP	SLC ĳ��	�β�)");
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
		System.out.println("(HDD�ε���	�뷮TB	ȸ����RPM	����	��ǰ	������	�𵨸�	���ۿ뷮	�뷮/�÷���	�������̽�)");
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
		System.out.println("(PSU �ε���	���� �뷮W	���� A/S ��	����	��ǰ	ȸ��	����	+12V ����	4�� IDE	SATA	PCI-E (6)	PCI-E(6+2)	Reform ���̺�	��ⷯ	������� 1W	�÷� ���̺�	������Ʈ	�Ŀ� �԰�	80 PLUS	��Ƽ�� PFC	����)");
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
		System.out.println("(CASE �ε���	ũ��	��mm	����mm	����mm	��	����	��ǰ	����	�ܺ� ����	���� ����	������)");
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
			System.out.println("(cooler �ε���	�ִ� ǳ��CFM	�ִ� ���� dBA	����	System Cooler	������	�� ũ��	�β�		���	Ŀ���� ��	�ּ� ȸ��	�ִ� ȸ��	�ּ� ����	���� �Ⱓ	LED ��	�߱� �κ�	���� ��	��ü ��)");
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