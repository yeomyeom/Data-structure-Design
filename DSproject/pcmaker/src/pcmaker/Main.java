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
		path = "GPUDB.csv";//GPU ��� ���� ����
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
				gpu_arr[i].point=p;							//��ġ����
				gpu_arr[i].price=database.nextDouble();		//����
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
			double point=(gpu_arr[0].point/gpu_arr[0].price);
			choose=0;
			for(int tmp=1; tmp<gpu_arr_size;tmp++)//GPU ����
			{
				if(point<(gpu_arr[tmp].point/gpu_arr[tmp].price))
				{
					choose = gpu_arr[tmp].index;
					edge_tdp = gpu_arr[tmp].tdp;
				}
			}
			result[0]=choose;//���� ����� ��¶� GPUDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
		}

		////////////////////////////////////////////////////////////////////////
		path = "CPUDB.csv";//CPU ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CPU ����
		CPU cpu_arr [] = new CPU[a];
		i=0;
		p=0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextInt();
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
			choose=0;
			for(int tmp=1; tmp<cpu_arr_size;tmp++)//GPU ����
			{
				if(point<(cpu_arr[tmp].point/cpu_arr[tmp].price))
				{
					choose = cpu_arr[tmp].index;
					edge = cpu_arr[tmp].soket;//CPU, MB�� ���� ����
					overclock = cpu_arr[tmp].over;//����Ŭ�� ���� ����
					edge_tdp_2=cpu_arr[tmp].tdp;
				}
			}
			result[1]=choose;//���� ����� ��¶� CPUDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
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
		while(database.nextInt()!=0)
		{
			if(edge.equals(database.next()))//CPU, MB ���� ����
			{
				mb_arr[i] =new MB();
				mb_arr[i].price = database.nextInt();			//����
				mb_arr[i].memory_slot = database.nextInt();	//�޸� ���� ���� MB RAM�� ���� ����
				mb_arr[i].ram_clock = database.nextDouble();	//�� Ŭ�� �� MB RAM�� ���� ����
				mb_arr[i].ddr_ver = database.nextInt();			//�� ddr ���� MB RAM�� ���� ����
				database.next();			//sata 3�� � ���� �����Ѱ� MB HDDSSD�� ���� ����
				database.next();			//���κ��� �߰���� 
				database.next();
				mb_arr[i].chipset = database.next();			//MB�� CASE�� ���� ���� ����
				for(int tmp=0;tmp<7;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(mb_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no MB soket matched CPU");
			scan.close();
			return;
		}
		else
		{
			double point=(mb_arr[0].ram_clock/mb_arr[0].price);
			choose=0;
			for(int tmp=1; tmp<mb_arr_size;tmp++)//GPU ����
			{
				if(point<(mb_arr[tmp].ram_clock/mb_arr[tmp].price))
				{
					choose = mb_arr[tmp].index;
					edge_ram1 = mb_arr[tmp].memory_slot;//MB,ram�� ���� ����
					edge_ram2 = mb_arr[tmp].ram_clock;//MB,ram�� ���� ����
					edge_ram3 = mb_arr[tmp].ddr_ver;//MB,ram�� ���� ����
					edge_case = mb_arr[tmp].chipset;//MB case�� ���� ����
				}
			}
			result[2]=choose;//���� ����� ��¶� MBDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "RAMDB.csv";//CPU ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CPU ����
		RAM ram_arr [] = new RAM[a];
		i=0;
		p=0;
		int q=0;
		double r=0.0;
		int l=0;
		n=1;
		while(database.nextInt()!=0)
		{
			p=database.nextInt();//�뷮
			q=database.nextInt();//����
			r=database.nextDouble();//Ŭ��
			l=database.nextInt();//ddr ����
			if(input.ram_vol<p && edge_ram1 < q && edge_ram2 < r && edge_ram3 == l)//Ram �뷮�� ����� �Է°����� ũ�ٸ�
			{
				ram_arr[i] =new RAM();
				ram_arr[i].vol = p;							//���뷮
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
			choose=0;
			for(int tmp=1; tmp<ram_arr_size;tmp++)//GPU ����
			{
				if(point<(ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price)
				{
					choose = ram_arr[tmp].index;//�������� ���̻� ������ ������ ����.
				}
			}
			result[3]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "SSDDB.csv";//SSD ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� SSD ����
		SSD ssd_arr [] = new SSD[a];
		i=0;
		p=0;
		while(database.nextInt()!=0)
		{
			ssd_arr[i] =new SSD();
			ssd_arr[i].rspeed = database.nextDouble();	//�б�ӵ�
			ssd_arr[i].wspeed = database.nextDouble();	//���� �ӵ�
			ssd_arr[i].as = database.nextDouble();		//as�Ⱓ
			ssd_arr[i].vol = database.nextDouble();     //�뷮
			ssd_arr[i].price = database.nextDouble();   //����
			for(int tmp=0;tmp<12;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
			{
				database.next();
			}
			ssd_arr[i].index=i+1;
			i++;
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
			double point=ssd_arr[0].as*ssd_arr[0].vol*(ssd_arr[0].rspeed+ssd_arr[0].wspeed)/ssd_arr[0].price;
			choose=0;
			for(int tmp=1; tmp<ssd_arr_size;tmp++)//GPU ����
			{
				if(point<ssd_arr[tmp].as*ssd_arr[tmp].vol*(ssd_arr[tmp].rspeed+ssd_arr[tmp].wspeed)/ssd_arr[tmp].price)
				{
					choose = ssd_arr[tmp].index;//�������� ���̻� ������ ������ ����.
				}
			}
			result[4]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
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
			choose=0;
			for(int tmp=1; tmp<hdd_arr_size;tmp++)//GPU ����
			{
				if(point<hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price)
				{
					choose = hdd_arr[tmp].index;//�������� ���̻� ������ ������ ����.
				}
			}
			result[5]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
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
			if((edge_tdp+edge_tdp_2)*1.2<k)//���ݿ뷮�� CPU+GPU *1.2�� �Ǿ� �����ϴ�
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
			choose=0;
			for(int tmp=1; tmp<psu_arr_size;tmp++)//GPU ����
			{
				if(point<(psu_arr[0].power*psu_arr[0].as)/psu_arr[0].price)
				{
					choose = psu_arr[tmp].index;//�������� ���̻� ������ ������ ����.
				}
			}
			result[6]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CASEDB.csv";//PSU ��� ���� ����
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
			double point=0;
			choose=0;
			if(input.cool == 1) {
				point = (case_arr[0].x*case_arr[0].y*case_arr[0].h)/case_arr[0].price;
				for(int tmp=1; tmp<case_arr_size;tmp++)//GPU ����
				{
					if(point<(case_arr[tmp].x*case_arr[tmp].y*case_arr[tmp].h)/case_arr[tmp].price)
					{
						choose = case_arr[tmp].index;//�������� ���̻� ������ ������ ����.
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
					}
				}
			}
			result[7]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
		}
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CoolerDB.csv";//cooler ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CASE ����
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
		database.close();//case ��� ���� ��
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
				choose=0;
				point = cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);
				for(int tmp=1; tmp<cooler_arr_size;tmp++)//cooler ����
				{
					if(point<cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price))
					{
						choose = cooler_arr[tmp].index;//�������� ���̻� ������ ������ ����.
					}
				}
		
				result[8]=choose;//���� ����� ��¶� RAMDB�� choose �ε��� ���� ������ �ִ� ��ǰ�� ��µ�
			}
		}else
		{
			result[8]=0;
		}
		////////////////////////////////////////////////////////////////////////////////////////
		//���� ��ºκ�
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
		System.out.println("(�ε���	��ġ��ũ	����	TDP(W)	����Ŭ�����ɿ���	����	CPU ǰ��	��ǰ��	������	�ھ� ��	������ ��	���ۼӵ�	�ͺ��ӵ�	L3ĳ��	���� �׷���	GPU�ӵ�(Mhz)	�ִ� �����޸�(Mhz))");
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
		System.out.println("(�ε���	��ġ��ũ	����	�����Ŀ�	��ǰ	������	��	�ھ�Ŭ��	�ν�Ʈ	�� ����	�޸�Ŭ��	����)");
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
		System.out.println("(�ε���	����	�ܰ�	�޸� ����	�� Ŭ�� ����	DDR����	SATA3	M.2	WiFi	������	���κ��� ǰ��	������	���� Ĩ��	����	��	SLI)");
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
		System.out.println("(�ε���	�뷮(GB)	����(��)	Ŭ��(Mhz)	�� �԰�(DDR)	����	RAM ǰ��	ȸ��	Ÿ�̹�	����	XMP	����	LED)");
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
		System.out.println("(�ε���	�б� �ӵ�MB/s	���� �ӵ�MB/s	A/S��	�뷮GB	����	SSD ��ǰ	���� ���	��Ʈ�ѷ�	������	TRIM	GC	S.M.A.R.T	ECC	DEVSLP	SLC ĳ��	�β�)");
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
		System.out.println("(�ε���	�뷮TB	ȸ����RPM	����	��ǰ	������	�𵨸�	���ۿ뷮	�뷮/�÷���	�������̽�)");
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
		System.out.println("(�ε���	���� �뷮W	���� A/S ��	����	��ǰ	ȸ��	����	+12V ����	4�� IDE	SATA	PCI-E (6)	PCI-E(6+2)	Reform ���̺�	��ⷯ	������� 1W	�÷� ���̺�	������Ʈ	�Ŀ� �԰�	80 PLUS	��Ƽ�� PFC	����)");
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
		System.out.println("(�ε���	ũ��	��mm	����mm	����mm	��	����	��ǰ	����	�ܺ� ����	���� ����	������)");
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
		System.out.println("(�ε���	�ִ� ǳ��CFM	�ִ� ���� dBA	����	System Cooler	������	�� ũ��	�β�		���	Ŀ���� ��	�ּ� ȸ��	�ִ� ȸ��	�ּ� ����	���� �Ⱓ	LED ��	�߱� �κ�	���� ��	��ü ��)");
		for(int tmp=0;tmp<19;tmp++)
		{
			System.out.print(database.next()+", ");
		}
		database.close();
		System.out.println("");
	}
	
}