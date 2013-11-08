package pal.game;

import pal.resource.Resource;
import pal.util.Convert;

public class Item {
	private static byte[] data = Resource.getItem();
	public static int getMax() {
		return data.length / 14;
	}
	
	private int id;
	private int roleId;
	private int gold;
	private int use;
	private int equip;
	private int drop;
	private int falg;
	
	public Item(int id) {
		this.id = id;
		roleId = Convert.getInt2(data, id*14+0);
		gold = Convert.getInt2(data, id*14+2);
		use = Convert.getInt2(data, id*14+4);
		roleId = Convert.getInt2(data, id*14+6);
		equip = Convert.getInt2(data, id*14+8);
		drop = Convert.getInt2(data, id*14+10);
		falg = Convert.getInt2(data, id*14+12);
	}	
		
	public int getDrop() {
		return drop;
	}
	public void setDrop(int drop) {
		this.drop = drop;
	}
	public int getEquip() {
		return equip;
	}
	public void setEquip(int equip) {
		this.equip = equip;
	}
	public int getFalg() {
		return falg;
	}
	public void setFalg(int falg) {
		this.falg = falg;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getUse() {
		return use;
	}
	public void setUse(int use) {
		this.use = use;
	}
		
}
