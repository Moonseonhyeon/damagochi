package damagochi;

public class Status implements Runnable{
	private Damagochi damagochi;
	 private int hungry;
	 private int strength;
	
	 public Status() {
			hungry = 0;
			strength = 100;
		}
	 
	 public Status(Damagochi damagochi, int hungry, int strength) {
		 this.damagochi = damagochi;
		this.hungry = hungry;
		this.strength = strength;
	}
		public int getHungry() {
			return hungry;
		}
		
		public int getStrength() {
			return strength;
		}
		
		public void setHungry(int hungry) {
			this.hungry = hungry;
		}
		
		public void setStrength(int strength) {
			this.strength = strength;
		}
		 
	@Override
		public void run() {
			damagoc
			
		}
}



//{
// private int hungry;
// private int strength;
// 
// public Status() {
//		hungry = 0;
//		strength = 100;
//	}
// 
// public Status(int hungry, int strength) {
//	this.hungry = hungry;
//	this.strength = strength;
//}
//
//public int getHungry() {
//	return hungry;
//}
//
//public int getStrength() {
//	return strength;
//}
//
//public void setHungry(int hungry) {
//	this.hungry = hungry;
//}
//
//public void setStrength(int strength) {
//	this.strength = strength;
//}
// 
//}
