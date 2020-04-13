package com.comunicacion;

import java.util.Comparator;

public class comparadorCargasHilos implements Comparator<hiloBalanceador>
{

	@Override
	public int compare(hiloBalanceador o1, hiloBalanceador o2)
	{
		int ret = -1;
		if (o1.getCargaDeMaquina() == o2.getCargaDeMaquina()) {
            ret = 0;
        } else if (o1.getCargaDeMaquina() > o2.getCargaDeMaquina()) {
            ret = -1;
        } else if (o1.getCargaDeMaquina() < o2.getCargaDeMaquina()) {
            ret = 1;
        }//end business logic
		return ret;
	}

}
