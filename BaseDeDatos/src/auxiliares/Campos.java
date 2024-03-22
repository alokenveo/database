package auxiliares;

public class Campos {
	private String nomCampo;
	private Object valor;

	public Campos() {
		super();
	}

	public Campos(String nomCampo, Object valor) {
		super();
		this.nomCampo = nomCampo;
		this.valor = valor;
	}

	public String getNomCampo() {
		return nomCampo;
	}

	public void setNomCampo(String nomCampo) {
		this.nomCampo = nomCampo;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Campos [nomCampo=" + nomCampo + ", valor=" + valor + "]";
	}
}
