/*
 * 2016年3月18日 
 */
package kevsn.config.beans;

/**
 * @author Kevin
 *
 */
public class WindowStyleDefinition {

	private String colorClass;

	private String backColor;

	private String foreColor;

	private String iconName;

	public String getColorClass() {
		return colorClass;
	}

	public void setColorClass(String colorClass) {
		this.colorClass = colorClass;
	}

	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getForeColor() {
		return foreColor;
	}

	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	@Override
	public String toString() {
		return "WindowStyleDefinition [colorClass=" + colorClass
				+ ", backColor=" + backColor + ", foreColor=" + foreColor
				+ ", iconName=" + iconName + "]";
	}

}
