package jp.sourceforge.pdt_tools.indentguide;

import jp.sourceforge.pdt_tools.indentguide.preferences.PreferenceConstants;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.themes.ColorUtil;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "jp.sourceforge.pdt_tools.indentGuide"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	// Line color resource
	private Color color;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		if (color != null) {
			color.dispose();
			color = null;
		}
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static void log(Throwable e) {
		getDefault().getLog().log(
				new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage()));
	}

	public Color getColor() {
		if (color == null) {
			String colorString = getPreferenceStore().getString(
					PreferenceConstants.LINE_COLOR);
			color = new Color(PlatformUI.getWorkbench().getDisplay(),
					ColorUtil.getColorValue(colorString));
		}
		return color;
	}

	public void setColor(Color color) {
		if (this.color != null) {
			this.color.dispose();
		}
		this.color = color;
	}

}
