import java.util.ArrayList;
import java.util.List;

class Garbage {
	
	private double d1 = 1;
	private double d2 = 2;
	
	// ���������GC�����������ʱ, �����һ��
	@Override
	public void finalize() {
		System.out.println(this + " collecting");
	}
}

public class JFRTest {
	
	
	public static void main(String[] args) {
		List<Garbage> list = new ArrayList<>();
		boolean flag = true;
		int count = 0;
		while (flag) {
			list.add(new Garbage());
			if (count++ == 20) {
				list.clear();
				
			}
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
