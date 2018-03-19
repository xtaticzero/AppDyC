package mx.gob.sat.siat.dyc.dao.ags;

import mx.gob.sat.siat.dyc.domain.ags.DyccDeptAgsDTO;

public interface DyccDeptAgsDAO
{
    DyccDeptAgsDTO encontrar(String deptId);
}
