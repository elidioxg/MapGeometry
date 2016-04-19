    package geocodigos.mapgeometry.Models;

    public class PointModel {
        private String id="",registro="", descricao="", latitude="",
                longitude="", norte="", leste="", setor="",
                altitude="", precisao = "",
                data="", hora="", selecionado="", latdms = "", londms="",
                order = "";

        public String getId() {
            return id;
        }

        public void setId(String id){
            this.id=id;
        }

        public String getRegistro() {
            return registro;
        }

        public void setRegistro(String registro) {
            this.registro = registro;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao= descricao;
        }

        public String getlatitude() {
            return latitude;
        }

        public void setLatidude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude= longitude;
        }

        public String getNorte(){
            return norte;
        }

        public void setNorte(String norte) {
            this.norte=norte;
        }

        public String getLeste() {
            return leste;
        }

        public void setLeste(String leste) {
            this.leste=leste;
        }

        public String getSetor() {
            return setor;
        }

        public void setSetor(String setor) {
            this.setor = setor;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude=altitude;
        }

        public String getPrecisao() {
            return precisao;
        }

        public void setPrecisao(String precisao) {
            this.precisao = precisao;
        }

        public String getData() { return data; }

        public void setData(String data) {
            this.data=data;
        }

        public String getHora() {return hora; }

        public void setHora(String hora) {
            this.hora = hora;
        }

        public String getSelecao(){return selecionado;}

        public void setSelecao(String selecionado) {
            this.selecionado=selecionado;
        }

        public String getLatDms() {
            return latdms;
        }

        public void setLatDms(String lat){
            this.latdms= lat;
        }

        public String getLonDms() {
            return londms;
        }

        public void setLonDms(String lon) {
            this.londms= lon;
        }

        public void setOrder(String order){
            this.order=order;
        }

        public String getOrder(){
            return this.order;
        }

}
