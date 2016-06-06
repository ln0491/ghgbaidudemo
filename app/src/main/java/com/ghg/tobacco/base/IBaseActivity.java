package com.ghg.tobacco.base;

/**
 * Activity接口
 * @author 曾繁添
 * @version 1.0
 *
 */
public interface IBaseActivity {

	/**
	 * 绑定视图的布局文件
	 * @return 布局文件资源id
	 */
	public int bindLayout();
	
	/**
	 * 初始化控件
	 */
	public void initView();
	/**
	 * 初始化监听
	 */
	public void initListener();
	
	/**
	 * 业务处理操作（onCreate方法中调用）
	 */
	public void initData();
	
	/**
	 * 暂停恢复刷新相关操作（onResume方法中调用）
	 */
	public void resume();

	public void pause();

	public void stop();
	/**
	 * 销毁、释放资源相关操作（onDestroy方法中调用）
	 */
	public void destroy();


}
