import java.util.Random;

public class Generator {

	private Random random;
	private int Gmaara;
	private boolean Gpienet_kirjaimet;
	private boolean Gisot_kirjaimet;
	private boolean Gnumerot;
	private boolean Gpohjoismaiset;
	private boolean Gerikoismerkit;
	private static int x;
	private static int y;
	//saadaan kaikki näkyviin...
	// nämä kaksi on suoria lainauksia, viitteet perässä.
	public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };//http://stackoverflow.com/questions/22273046/how-to-print-the-extended-ascii-code-in-java-from-integer-value
	// nämä kaksi on suoria lainauksia, viitteet perässä.
	public static final char getAscii(int code) {
	    if (code >= 0x80 && code <= 0xFF) {
	        return EXTENDED[code - 0x7F];
	    }
	    return (char) code;
	}//http://stackoverflow.com/questions/22273046/how-to-print-the-extended-ascii-code-in-java-from-integer-value
	
	public Generator(int gmaara, boolean gpienet_kirjaimet, boolean gisot_kirjaimet, boolean gnumerot, boolean gpohjoismaiset, boolean gerikoismerkit){
		this.random = new Random();
		this.Gmaara = gmaara;
		this.Gpienet_kirjaimet = gpienet_kirjaimet;		//97-122 case1
		this.Gisot_kirjaimet = gisot_kirjaimet;			//65-90 case2
		this.Gnumerot = gnumerot;						//48-57 case3
		this.Gpohjoismaiset = gpohjoismaiset;			//142/132, 143/134, 153/148 case4,case5
		this.Gerikoismerkit = gerikoismerkit;			//33-47,58-64	case6,case7
	}
	
	
	
	public String generate(){
		//tätä kutsutaan
		System.setProperty("file.encoding","UTF-8");
		StringBuilder  salasana = new StringBuilder();
		if (Gpienet_kirjaimet || Gisot_kirjaimet || Gnumerot || Gpohjoismaiset || Gerikoismerkit){//tarkista parametrit
		while(salasana.length()<=Gmaara){
			int [] jono = kategoria();//arvo case
			jono[0]=x;
			jono[1]=y;
			char merkki = oneChar(x,y);		//arpoo merkin väliltä [x,y[
			salasana.append(merkki);	// lisää merkki salikseen
		}
		return salasana.toString();//tämä annetaan
		}
		else{
			return "no parametrs!";
		}
	}
	// onnetar
	public char oneChar(int x, int y){
		char merkki = (char) (this.random.nextInt(y-x)+x);
		return merkki;
	}
	//määrää rajat
	public int[] kategoria(){
		x=0;
		y=0;
		while(x*y==0){//tehdään kunnes saadaan väli[x,y]
			int kategoria = this.random.nextInt(6)+1;//arpoo case:in
			switch(kategoria){
				case 1://pienet
					if(Gpienet_kirjaimet){
					x=97;
					y=123;
					break;
					}
				case 2://isot
					if(Gisot_kirjaimet){
						x=65;
						y=91;
						break;}
				case 3://numerot
					if(Gnumerot){
						x=48;
						y=58;
						break;}
				case 4://pohjoismaiset isot ja pienet
					if(Gpohjoismaiset){
						int [] lista = {141,142,152,131,133,147};
						x=lista[this.random.nextInt(6)];
						x=getAscii(x);
						y=x+1;
						break;
						}
				case 5://erikoios 1
					if(Gerikoismerkit){
						x=33;
						y=48;
						break;}
				case 6://erikois 2
					if(Gerikoismerkit){
						x=58;
						y=65;
						break;}
			}
		}
		int[] jono = {x,y};
		return jono;
	}
}