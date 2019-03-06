/*
 * 2016年3月18日 
 */
package kevsn.config.beans;

/**
 * @author Kevin
 *
 */
public class DefaultWindowManager implements WindowManager {
	// are windows allowed to be resized?
	private boolean resizable;
	// do windows have a close button?
	private boolean closable;

	// Default size of new windows
	private int defaultWidth;

	private int defaultHeight;

	private WindowStyleDefinition styleDefinition;

	public boolean isClosable() {
		return closable;
	}

	public void setClosable(boolean closable) {
		this.closable = closable;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public int getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultWidth(int defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public int getDefaultHeight() {
		return defaultHeight;
	}

	public void setDefaultHeight(int defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	public WindowStyleDefinition getStyleDefinition() {
		return styleDefinition;
	}

	public void setStyleDefinition(WindowStyleDefinition styleDefinition) {
		this.styleDefinition = styleDefinition;
	}

	@Override
	public String toString() {
		return "DefaultWindowManager [resizable=" + resizable + ", closable="
				+ closable + ", defaultWidth=" + defaultWidth
				+ ", defaultHeight=" + defaultHeight + ", styleDefinition="
				+ styleDefinition + "]";
	}

}
