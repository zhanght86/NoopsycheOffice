package BP.Sys.Frm;

import java.util.ArrayList;
import java.util.List;

import BP.En.EntitiesMyPK;
import BP.En.Entity;

/**
 * 附件s
 */
public class FrmAttachments extends EntitiesMyPK
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 构造
	/**
	 * 附件s
	 */
	public FrmAttachments()
	{
	}
	
	public static ArrayList<FrmAttachment> convertFrmAttachments(Object obj)
	{
		return (ArrayList<FrmAttachment>) obj;
	}
	public List<FrmAttachment> ToJavaList()
	{
		return (List<FrmAttachment>)(Object)this;
	}
	
	/**
	 * 附件s
	 * 
	 * @param fk_mapdata
	 *            s
	 */
	public FrmAttachments(String fk_mapdata)
	{
		this.Retrieve(FrmAttachmentAttr.FK_MapData, fk_mapdata,
				FrmAttachmentAttr.FK_Node, 0);
	}
	
	/**
	 * 得到它的 Entity
	 */
	@Override
	public Entity getGetNewEntity()
	{
		return new FrmAttachment();
	}
}