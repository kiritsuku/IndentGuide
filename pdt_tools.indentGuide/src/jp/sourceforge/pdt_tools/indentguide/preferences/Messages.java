package jp.sourceforge.pdt_tools.indentguide.preferences;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "jp.sourceforge.pdt_tools.indentguide.preferences.messages"; //$NON-NLS-1$

	public static String IndentGuidePreferencePage_description;
	public static String IndentGuidePreferencePage_enabled_label;
	public static String IndentGuidePreferencePage_group_label;
	public static String IndentGuidePreferencePage_alpha_label;
	public static String IndentGuidePreferencePage_style_label;
	public static String IndentGuidePreferencePage_style_solid;
	public static String IndentGuidePreferencePage_style_dash;
	public static String IndentGuidePreferencePage_style_dot;
	public static String IndentGuidePreferencePage_style_dash_dot;
	public static String IndentGuidePreferencePage_style_dash_dot_dot;
	public static String IndentGuidePreferencePage_width_label;
	public static String IndentGuidePreferencePage_shift_label;
	public static String IndentGuidePreferencePage_color_label;
	public static String IndentGuidePreferencePage_group2_label;
	public static String IndentGuidePreferencePage_draw_left_end_label;
	public static String IndentGuidePreferencePage_draw_blank_line_label;
	public static String IndentGuidePreferencePage_skip_comment_block_label;
	public static String IndentGuidePreferencePage_group3_label;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
