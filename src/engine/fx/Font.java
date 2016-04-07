package engine.fx;

public enum Font
{
	STANDARD("/fonts/standard.png");
	
	public final int NUM_UNICODES = 59; //amount of charaters
	public int[] offsets = new int[NUM_UNICODES];
	public int[] widths = new int[NUM_UNICODES];
	public Image image;
	
	Font(String path)
	{
		image = new Image(path);
		
		int unicode = -1;
		
		for(int x = 0; x < image.width; x++)
		{
			int color = image.pixels[x];
			
			if(color == 0xff0000ff)
			{
				unicode++;
				offsets[unicode] = x;
			}
			
			if (color == 0xffffff00)
			{
				widths[unicode] = x - offsets[unicode];
			}
		}
	}
	
	public static int getStringSize(Font font, String s)
	{
		// in case you don't have lower case letters in font
		s = s.toUpperCase();
		
		int offset = 0;
		
		for (int i = 0; i < s.length(); i++)
		{
			int unicode = s.codePointAt(i) - 32;
			offset += font.widths[unicode];
		}
		return offset;
	}
}
