package com.bigkoo.pickerview.adapter;


import com.contrarywind.adapter.WheelAdapter;

/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter2 implements WheelAdapter {

	private int minValue;
	private int maxValue;

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public NumericWheelAdapter2(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public Object getItem(int index) {
			int value = minValue + index;
			if (index==0) {
			return 0;
			}else if (index==1) {
			return 15;
			}else if (index==2) {
				return 30;
			}else{
				return 45;
			}
	}

	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}
	
	@Override
	public int indexOf(Object o){
		try {
			return (int)o - minValue;
		} catch (Exception e) {
			return -1;
		}

	}
}
