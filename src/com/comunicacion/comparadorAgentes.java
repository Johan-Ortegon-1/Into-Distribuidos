package com.comunicacion;

import java.util.Comparator;

public class comparadorAgentes implements Comparator<Agente>
{

	@Override
	public int compare(Agente o1, Agente o2)
	{
		int ret = -1;
		if (o1.getMiPais().getPoblacionTotal() == o2.getMiPais().getPoblacionTotal()) {
            ret = 0;
        } else if (o1.getMiPais().getPoblacionTotal() > o2.getMiPais().getPoblacionTotal()) {
            ret = 1;
        } else if (o1.getMiPais().getPoblacionTotal() < o2.getMiPais().getPoblacionTotal()) {
            ret = -1;
        }//end business logic
		return ret;
	}

}
