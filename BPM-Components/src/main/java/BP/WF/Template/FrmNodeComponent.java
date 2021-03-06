package BP.WF.Template;

import BP.DA.DataType;
import BP.En.EnType;
import BP.En.Entity;
import BP.En.Map;
import BP.En.UAC;
import BP.Sys.Frm.GroupCtrlType;
import BP.Sys.Frm.GroupField;
import BP.Sys.Frm.GroupFieldAttr;

public class FrmNodeComponent extends Entity{
	/** 
	 节点属性.
	*/
	public final String getNo()
	{
		return "ND" + this.getNodeID();
	}
	public final void setNo(String value)
	{
		String nodeID = value.replace("ND", "");
		this.setNodeID(Integer.parseInt(nodeID));
	}
	/** 
	 节点ID
	*/
	public final int getNodeID()
	{
		return this.GetValIntByKey(NodeAttr.NodeID);
	}
	public final void setNodeID(int value)
	{
		this.SetValByKey(NodeAttr.NodeID, value);
	}
	
	/** 
	 控制
	*/
	@Override
	public UAC getHisUAC()
	{
		UAC uac = new UAC();
		uac.OpenForSysAdmin();
		uac.IsDelete = false;
		uac.IsInsert = false;
		return uac;
	}
	/** 
	 重写主键
	*/
	@Override
	public String getPK()
	{
		return "NodeID";
	}
	/** 
	 节点表单组件
	*/
	public FrmNodeComponent()
	{
	}
	/** 
	 节点表单组件
	 
	 @param no
	*/
	public FrmNodeComponent(String mapData)
	{
		String mapdata = mapData.replace("ND", "");
		if (DataType.IsNumStr(mapdata) == false)
		{
			return;
		}

		try
		{
			this.setNodeID(Integer.parseInt(mapdata));
		}
		catch (java.lang.Exception e)
		{
			return;
		}
		this.Retrieve();
	}
	/** 
	 节点表单组件
	 
	 @param no
	*/
	public FrmNodeComponent(int nodeID)
	{
		this.setNodeID(nodeID);
		this.Retrieve();
	}
	/** 
	 EnMap
	*/
	@Override
	public Map getEnMap()
	{
		if (this.get_enMap() != null)
		{
			return this.get_enMap();
		}

		Map map = new Map("WF_Node", "节点表单组件");
		map.Java_SetEnType(EnType.Sys);

		map.AddTBIntPK(NodeAttr.NodeID, 0, "节点ID", true, true);
		map.AddTBString(NodeAttr.Name, null, "节点名称", true,true, 0, 100, 10);

		FrmWorkCheck fwc = new FrmWorkCheck();
		map.AddAttrs(fwc.getEnMap().getAttrs());

		FrmSubFlow subflow = new FrmSubFlow();
		map.AddAttrs(subflow.getEnMap().getAttrs());

		FrmThread thread = new FrmThread();
		map.AddAttrs(thread.getEnMap().getAttrs());

			//轨迹组件.
		FrmTrack track = new FrmTrack();
		map.AddAttrs(track.getEnMap().getAttrs());

			//流转自定义组件.
		FrmTransferCustom ftt = new FrmTransferCustom();
		map.AddAttrs(ftt.getEnMap().getAttrs());

		this.set_enMap(map);
		return this.get_enMap();
	}

	@Override
	protected boolean beforeUpdate()
	{
		GroupField gf = new GroupField();

		//#region 审核组件.
		FrmWorkCheck fwc = new FrmWorkCheck(this.getNodeID());
		fwc.Copy(this);

		if (fwc.getHisFrmWorkCheckSta() == FrmWorkCheckSta.Disable)
		{
			gf.Delete(GroupFieldAttr.CtrlID, "FWC" + this.getNo());
		}
		else
		{
			if (gf.IsExit(GroupFieldAttr.CtrlID, "FWC" + this.getNo()) == false)
			{
				gf = new GroupField();
				gf.setEnName("ND" + this.getNodeID());
				gf.setCtrlID("FWC" + this.getNo());
				gf.setCtrlType(GroupCtrlType.FWC);
				gf.setLab("审核组件");
				gf.setIdx(0);
				gf.Insert(); //插入.
			}
		}
		//#endregion 审核组件.

		//#region 父子流程组件.
		FrmSubFlow subflow = new FrmSubFlow(this.getNodeID());
		subflow.Copy(this);

		if (subflow.getHisFrmSubFlowSta() == FrmSubFlowSta.Disable)
		{
			gf.Delete(GroupFieldAttr.CtrlID, "SubFlow" + this.getNo());
		}
		else
		{
			if (gf.IsExit(GroupFieldAttr.CtrlID, "SubFlow" + this.getNo()) == false)
			{
				gf = new GroupField();
				gf.setEnName("ND" + this.getNodeID());
				gf.setCtrlID("SubFlow" + this.getNo());
				gf.setCtrlType(GroupCtrlType.SubFlow);
				gf.setLab("父子流程组件");
				gf.setIdx(0);
				gf.Insert(); //插入.
			}
		}
		//#endregion 父子流程组件.

		//#region 处理轨迹组件.
		FrmTrack track = new FrmTrack(this.getNodeID());
		track.Copy(this);
		if (track.getFrmTrackSta() == FrmTrackSta.Disable)
		{
			gf.Delete(GroupFieldAttr.CtrlID, "FrmTrack" + this.getNo());
		}
		else
		{
			if (gf.IsExit(GroupFieldAttr.CtrlID, "FrmTrack" + this.getNo()) == false)
			{
				gf = new GroupField();
				gf.setEnName("ND" + this.getNodeID());
				gf.setCtrlID("FrmTrack" + this.getNo());
				gf.setCtrlType(GroupCtrlType.Track);
				gf.setLab("轨迹");
				gf.setIdx(0);
				gf.Insert(); //插入.
			}
		}
		//#endregion 处理轨迹组件.

		//#region 子线程组件.
		FrmThread thread = new FrmThread(this.getNodeID());
		thread.Copy(this);

		if (thread.getFrmThreadSta() == FrmThreadSta.Disable)
		{
			gf.Delete(GroupFieldAttr.CtrlID, "FrmThread" + this.getNo());
		}
		else
		{
			if (gf.IsExit(GroupFieldAttr.CtrlID, "FrmThread" + this.getNo()) == false)
			{
				gf = new GroupField();
				gf.setEnName("ND" + this.getNodeID());
				gf.setCtrlID("FrmThread" + this.getNo());
				gf.setCtrlType(GroupCtrlType.Thread);
				gf.setLab("子线程");
				gf.setIdx(0);
				gf.Insert(); //插入.
			}
		}
		//#endregion 子线程组件.

		//#region 子线程组件.
		FrmTransferCustom ftc = new FrmTransferCustom(this.getNodeID());
		ftc.Copy(this);

		if (ftc.getFrmTransferCustomSta() == FrmTransferCustomSta.Disable)
		{
			gf.Delete(GroupFieldAttr.CtrlID, "FrmFTC" + this.getNo());
		}
		else
		{
			if (gf.IsExit(GroupFieldAttr.CtrlID, "FrmFTC" + this.getNo()) == false)
			{
				gf = new GroupField();
				gf.setEnName("ND" + this.getNodeID());
				gf.setCtrlID("FrmFTC" + this.getNo());
				gf.setCtrlType(GroupCtrlType.FTC);
				gf.setLab("流转自定义");
				gf.setIdx(0);
				gf.Insert(); //插入.
			}
		}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
			///#endregion 子线程组件.

		return super.beforeUpdate();
	}

}
