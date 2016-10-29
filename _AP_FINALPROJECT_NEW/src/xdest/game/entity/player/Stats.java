package xdest.game.entity.player;

public class Stats {

	private double maxhp,bonushp,eresist,presist,basedamage,bonusdamage,bonuspresist;
	
	
	
	public Stats() {
		maxhp = 1000;
		basedamage = 35;
		bonusdamage = 0;
		bonushp = 0;
		eresist = 0;
		presist = 25;
		bonuspresist=0;
	}
	
	public Stats(double bhp, double pr, double er, double bd)
	{
		maxhp = 1000;
		basedamage = 35;
		this.bonusdamage = bd;
		this.bonushp = bhp;
		this.presist = pr;
		bonuspresist=0;
		this.eresist = er;
	}
	
	public void setBonusPResist(double p)
	{
		this.bonuspresist = p;
	}
	
	public void addBonusHp(double hp)
	{
		bonushp+=hp;
	}
	
	public void addEResist(double eresist)
	{
		this.eresist+=eresist;
	}
	
	public void addPResist(double presist)
	{
		this.presist+=presist;
	}
	
	public void addBonusDamage(double d)
	{
		this.bonusdamage+=d;
	}
	
	public double getEResist()
	{
		return this.eresist;
	}
	
	public double getPResist()
	{
		return this.presist+bonuspresist;
	}
	
	public double getMaxHp()
	{
		return this.maxhp + bonushp;
	}
	
	public double getBaseHp()
	{
		return this.maxhp;
	}
	
	public double getBonusHp()
	{
		return this.bonushp;
	}
	
	public double getDamage()
	{
		return this.basedamage + this.bonusdamage;
	}
	
	public double getBonusDamage()
	{
		return this.bonusdamage;
	}
	
	public double getBaseDamage()
	{
		return this.basedamage;
	}

}
