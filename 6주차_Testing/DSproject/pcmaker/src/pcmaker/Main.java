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
			input.cpu = 1000;
			input.gpu = 0;
			input.ram_vol = 4;
			input.cool = 1;//������ ����� ���̽��� ���ٺ��� ������ ���� �ȵǰ� �������.
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
			path = "GPUDB.csv";//GPU ��� ���� ����
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
					gpu_arr[i].point=p;//��ġ����
					gpu_arr[i].price=database.nextDouble();		//����
					gpu_arr[i].tdp=database.nextDouble();		//�Һ�����
					gpu_arr[i].name=database.next();			//��ǰ ��
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
			if(gpu_arr_size == 0)//���� �˻�
			{
				System.out.println("Wrong GPU banchmark point");
				scan.close();
				return;
			}
			else
			{
				double point=gpu_arr[0].point/gpu_arr[0].price;
				for(int tmp=1; tmp<gpu_arr_size;tmp++)//GPU ����
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
				System.out.println("GPU ���� " +gpu_arr[choose].price);
				System.out.println("GPU �����Ŀ�(W) " +gpu_arr[choose].tdp);
				System.out.println("GPU �̸� " +gpu_arr[choose].name);
				System.out.println("GPU================");
				}
			database.close();//GPU ��� ���� ����
		}
		////////////////////////////////////////////////////////////////////////
		path = "CPUDB.csv";//CPU ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CPU ����
		CPU cpu_arr [] = new CPU[a];
		i=0;
		p=0.0;
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
				cpu_arr[i].name = database.next();
				for(int tmp=0;tmp<11;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(cpu_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong CPU banchmark point");
			scan.close();
			return;
		}
		else
		{
			double point=cpu_arr[0].point/cpu_arr[0].price;
			for(int tmp=1; tmp<cpu_arr_size;tmp++)//GPU ����
			{
				if(point<cpu_arr[tmp].point/cpu_arr[tmp].price)
				{
					choose = tmp;
					sum = cpu_arr[tmp].price;
					edge = cpu_arr[tmp].soket;//CPU, MB�� ���� ����
					overclock = cpu_arr[tmp].over;//����Ŭ�� ���� ����
					edge_tdp_2=cpu_arr[tmp].tdp;
					point = cpu_arr[tmp].point/cpu_arr[tmp].price;
				}
			}
			sum_result = sum_result+sum;
			System.out.println("price now CPU: " +sum_result);
			System.out.println("CPU================");
			System.out.println("CPU �̸� " +cpu_arr[choose].name);
			System.out.println("CPU ���� " +cpu_arr[choose].price);
			System.out.println("CPU �Һ����� " +cpu_arr[choose].tdp);
			System.out.println("CPU ����Ŭ�� ���� (Yes =1 No =0)" +cpu_arr[choose].over);
			System.out.println("CPU ���Ϲ��� " +cpu_arr[choose].soket);
			System.out.println("CPU================");
		}
		database.close();//CPU ��� ���� ��
		/////////////////////////////////////////////////////////////////////////
		path="MBDB.csv";
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a=database.nextInt();
		MB mb_arr[]=new MB[a];//MB ��� ����
		i=0;
		p=0;
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
				mb_arr[i].soket = s;
				mb_arr[i].memory_slot = database.nextInt();	//�޸� ���� ���� MB RAM�� ���� ����
				mb_arr[i].ram_clock = database.nextDouble();	//�� Ŭ�� �� MB RAM�� ���� ����
				mb_arr[i].ddr_ver = database.nextInt();			//�� ddr ���� MB RAM�� ���� ����
				mb_arr[i].paze=database.nextInt();
				mb_arr[i].chipset = database.next();			//MB�� CASE�� ���� ���� ����
				mb_arr[i].name = database.next();
				for(int tmp=0;tmp<6;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(mb_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no MB soket matched CPU");
			scan.close();
			return;
		}
		else
		{
			double point=(mb_arr[0].paze*mb_arr[0].ram_clock/mb_arr[0].price);
			for(int tmp=1; tmp<mb_arr_size;tmp++)//mb ����
			{
				if(point<mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price)
				{
					choose = tmp;
					sum = mb_arr[tmp].price;
					edge_ram1 = mb_arr[tmp].memory_slot;//MB,ram�� ���� ����
					edge_ram2 = mb_arr[tmp].ram_clock;//MB,ram�� ���� ����
					edge_ram3 = mb_arr[tmp].ddr_ver;//MB,ram�� ���� ����
					edge_case = mb_arr[tmp].chipset;//MB case�� ���� ����
					point = mb_arr[tmp].paze*mb_arr[tmp].ram_clock/mb_arr[tmp].price;
				}
			}
			sum_result = sum_result +sum;
			System.out.println("price now MB: " +sum_result);
			System.out.println("MB================");
			System.out.println("MB �̸� " +mb_arr[choose].name);
			System.out.println("MB ���� " +mb_arr[choose].price);
			System.out.println("MB ���� " +mb_arr[choose].soket);
			System.out.println("MB �޸� ���� ���� " +mb_arr[choose].memory_slot);
			System.out.println("MB �� ���� Ŭ�� " +mb_arr[choose].ram_clock);
			System.out.println("MB �� ���� ����(DDR4=4 DDR3=3) " +mb_arr[choose].ddr_ver);
			System.out.println("MB ������(������) " +mb_arr[choose].paze);
			System.out.println("MB Ĩ�� " +mb_arr[choose].chipset);
			System.out.println("MB================");
		}
		database.close();//MB ��
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
		while(database.nextInt()!=0)
		{
			p=database.nextDouble();//�뷮
			q=database.nextInt();//����
			r=database.nextDouble();//Ŭ��
			l=database.nextInt();//ddr ����
			if(input.ram_vol<=p && input.ram_vol*4>=p && edge_ram1 >= q && edge_ram2 <= r*2 && edge_ram3 == l)//Ram �뷮�� ����� �Է°����� ũ�ٸ�&& edge_ram2/2 <= r
			{
				ram_arr[i] =new RAM();
				ram_arr[i].vol = p;		
				ram_arr[i].ram_number = q;	//������
				ram_arr[i].ram_clock = r;		//����Ŭ�� ���� ���κ��� �����Ҷ� ������
				ram_arr[i].ddr_ver = l;			//���� ���� ���κ��� �����Ҷ� ������
				ram_arr[i].price = database.nextDouble();	//����
				ram_arr[i].name = database.next();
				for(int tmp=0;tmp<7;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(ram_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no RAM matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=(ram_arr[0].vol*ram_arr[0].ram_clock/ram_arr[0].price);
			for(int tmp=1; tmp<ram_arr_size;tmp++)//ram ����
			{
				if(point<(ram_arr[tmp].vol*ram_arr[tmp].ram_clock)/ram_arr[tmp].price)
				{
					choose = tmp;//�������� ���̻� ������ ������ ����.
					sum = ram_arr[tmp].price;
					point = ram_arr[tmp].vol*ram_arr[tmp].ram_clock/ram_arr[tmp].price;
				}
			}
			sum_result = sum_result +sum;
			System.out.println("price now ram: " +sum_result);
			System.out.println("RAM================");
			System.out.println("RAM �̸� " +ram_arr[choose].name);
			System.out.println("RAM ���� " +ram_arr[choose].price);
			System.out.println("RAM �뷮 " +ram_arr[choose].vol);
			System.out.println("RAM ����" +ram_arr[choose].ram_number);
			System.out.println("RAM Ŭ��(MHz) " +ram_arr[choose].ram_clock);
			System.out.println("RAM ����(DDR4=4, DDR3=3) " +ram_arr[choose].ddr_ver);
			System.out.println("RAM================");
		}
		database.close();//ram ��� ��
		////////////////////////////////////////////////////////////////////////////////////////
		if(input.ssd_vol != 0) {
			path = "SSDDB.csv";//SSD ��� ���� ����
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();// �� SSD ����
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
					ssd_arr[i].rspeed = database.nextDouble();	//�б�ӵ�
					ssd_arr[i].wspeed = database.nextDouble();	//���� �ӵ�
					ssd_arr[i].as = database.nextDouble();		//as�Ⱓ
					ssd_arr[i].price = database.nextDouble();   //����
					ssd_arr[i].name = database.next();
					for(int tmp=0;tmp<11;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
			if(ssd_arr_size == 0)//���� �˻�
			{
				System.out.println("Wrong no SSD matched MB");
				scan.close();
				return;
			}
			else
			{//ssd_arr[0].as*
				double point = (ssd_arr[0].rspeed+ssd_arr[0].wspeed)*ssd_arr[0].vol/ssd_arr[0].price;
				for(int tmp=1; tmp<ssd_arr_size;tmp++)//GPU ����
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
				System.out.println("SSD �̸� " +ssd_arr[choose].name);
				System.out.println("SSD ���� " +ssd_arr[choose].price);
				System.out.println("SSD �뷮 " +ssd_arr[choose].vol);
				System.out.println("SSD �б�ӵ�(MB/s) " +ssd_arr[choose].rspeed);
				System.out.println("SSD ����ӵ�(MB/s) " +ssd_arr[choose].wspeed);
				System.out.println("SDD AS�Ⱓ " +ssd_arr[choose].as);
				System.out.println("SSD================");
			}
			database.close();//SSD
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
			hdd_arr[i].name = database.next();
			for(int tmp=0;tmp<6;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
			{
				database.next();
			}
			i++;
		}
		int hdd_arr_size = i;
		if(hdd_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no HDD matched MB");
			scan.close();
			return;
		}
		else
		{
			double point=hdd_arr[0].vol*hdd_arr[0].rpm/hdd_arr[0].price;
			for(int tmp=1; tmp<hdd_arr_size;tmp++)//hdd ����
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
			System.out.println("HDD �̸� " +hdd_arr[choose].name);
			System.out.println("HDD ���� " +hdd_arr[choose].price);
			System.out.println("HDD �뷮 " +hdd_arr[choose].vol);
			System.out.println("HDD ��ũ �ӵ�(rpm) " +hdd_arr[choose].rpm);
			System.out.println("HDD================");
		}
		database.close();//hdd ��� ��
		////////////////////////////////////////////////////////////////////////////////////////
		path = "PSUDB.csv";//PSU ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� SSD ����
		PSU psu_arr [] = new PSU[a];
		i=0;
		p=0;
		double k=0.0;
		System.out.println("CPU " +edge_tdp_2+" GPU " +edge_tdp);
		if(edge_tdp != 0) {
			while(database.nextInt()!=0)
			{
				k=database.nextDouble();
				if((edge_tdp+edge_tdp_2)*1.2<=k)//���ݿ뷮�� CPU+GPU *1.2�� �Ǿ� �����ϴ�
				{
					psu_arr[i] =new PSU();
					psu_arr[i].power = k;
					psu_arr[i].as = database.nextDouble();						
					psu_arr[i].price = database.nextDouble();
					psu_arr[i].name = database.next();
					for(int tmp=0;tmp<17;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
				if(edge_tdp_2*5.0>=k)//cpu �ϳ��� �����
				{
					psu_arr[i] =new PSU();
					psu_arr[i].power = k;
					psu_arr[i].as = database.nextDouble();						
					psu_arr[i].price = database.nextDouble();
					psu_arr[i].name = database.next();
					for(int tmp=0;tmp<17;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(psu_arr_size == 0)//���� �˻�
		{
			System.out.println("Wrong no PSU matched CPU+GPU");
			scan.close();
			return;
		}
		else
		{
			double point=psu_arr[0].power*psu_arr[0].as/psu_arr[0].price;
			for(int tmp=1; tmp<psu_arr_size;tmp++)//GPU ����
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
			System.out.println("PSU �̸� " +psu_arr[choose].name);
			System.out.println("PSU ���� " +psu_arr[choose].price);
			System.out.println("PSU ���ݿ뷮 " +psu_arr[choose].power);
			System.out.println("PSU AS�Ⱓ " +psu_arr[choose].as);
			System.out.println("PSU================");
		}
		database.close();//psu ���  ��
		////////////////////////////////////////////////////////////////////////////////////////
		path = "CASEDB.csv";//case ��� ���� ����
		database = new Scanner(new File(path));
		database.useDelimiter(",");
		a = database.nextInt();// �� CASE ����
		CASE case_arr [] = new CASE[a];
		i=0;
		p=0;
		while(database.nextInt()!=0)
		{
			if(edge_case.equals(database.next())) {
				case_arr[i] =new CASE();
				if(input.cool==1)//cooling�� ����
				{
					case_arr[i].x = database.nextDouble();	
					case_arr[i].y = database.nextDouble();
					case_arr[i].h = database.nextDouble();
					case_arr[i].cooler = database.nextDouble();
					case_arr[i].price = database.nextDouble();	
					case_arr[i].name = database.next();
				}else//silence�� ����
				{
					database.nextDouble();
					database.nextDouble();
					database.nextDouble();
					case_arr[i].cooler=database.nextDouble();
					case_arr[i].price = database.nextDouble();
					case_arr[i].name = database.next();
				}
				for(int tmp=0;tmp<5;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
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
		if(case_arr_size == 0)//���� �˻�
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
				for(int tmp=1; tmp<case_arr_size;tmp++)//case ����
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
				System.out.println("case�� cooling �켱���� �����Ͽ����ϴ�.");
				System.out.println("case �̸� " +case_arr[choose].name);
				System.out.println("case ���� " +case_arr[choose].price);
				System.out.println("case ����" +case_arr[choose].x);
				System.out.println("case ����" +case_arr[choose].y);
				System.out.println("case ����" +case_arr[choose].h);
				System.out.println("case �� ����" +case_arr[choose].cooler);
				System.out.println("CASE================");
				
			}else
			{
				point = case_arr[0].price/case_arr[0].cooler;
				for(int tmp=1; tmp<case_arr_size;tmp++)//GPU ����
				{
					if(point<case_arr[tmp].price/case_arr[tmp].cooler)
					{
						choose = tmp;//�������� ���̻� ������ ������ ����.
						sum = case_arr[tmp].price;
						point = case_arr[tmp].price/case_arr[tmp].cooler;
					}
				}
				sum_result = sum_result +sum;
				System.out.println("price now case: " +sum_result);
				System.out.println("CASE================");
				System.out.println("case�� ������ �켱���� �����Ͽ����ϴ�.");
				System.out.println("case �̸� " +case_arr[choose].name);
				System.out.println("case ���� " +case_arr[choose].price);
				System.out.println("case �� ����" +case_arr[choose].cooler);
				System.out.println("CASE================");
			}
		}
		database.close();//case ��� ��
		////////////////////////////////////////////////////////////////////////////////////////
		if(input.cool == 1) {
			path = "CoolerDB.csv";//cooler ��� ���� ����
			database = new Scanner(new File(path));
			database.useDelimiter(",");
			a = database.nextInt();// �� cooler����
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
				for(int tmp=0;tmp<14;tmp++)					//��ǰ ���� ��� ���� ������ + /n pass
				{
					database.next();
				}
				i++;
			}
			int cooler_arr_size = i;
			if(cooler_arr_size == 0)//���� �˻�
			{
				System.out.println("Wrong no Cooler matched CASE");
				scan.close();
				return;
			}
			else
			{
				double point=cooler_arr[0].air/(cooler_arr[0].noisy*cooler_arr[0].price);;
				for(int tmp=1; tmp<cooler_arr_size;tmp++)//cooler ����
				{
					if(point<cooler_arr[tmp].air/(cooler_arr[tmp].noisy*cooler_arr[tmp].price))
					{
						choose = tmp;//cooler������ ���̻� ������ ������ ����.
						sum = cooler_arr[tmp].price;
						point = cooler_arr[tmp].air/(cooler_arr[tmp].noisy*cooler_arr[tmp].price);
					}
				}
				sum_result = sum_result +sum;
				System.out.println("price now cooler: " +sum_result);
				System.out.println("Cooler================");
				System.out.println("case�� cooling �켱���� �����Ͽ����ϴ�.");
				System.out.println("cooler �̸� " +cooler_arr[choose].name);
				System.out.println("cooler ���� " +cooler_arr[choose].price);
				System.out.println("cooler ����(dBA) " +cooler_arr[choose].noisy);
				System.out.println("cooler ǳ�� " +cooler_arr[choose].air);
				System.out.println("Cooler================");
			}
			database.close();//cooler ��� ��
		}
	////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("�� ���� : "+sum_result);
	}
}