    package geocodigos.mapgeometry.Models;

    public class PointModel {
        private String id = "", name = "", description = "", latitude = "",
                longitude = "", north = "", east = "", sector = "",
                altitude = "", precision = "",
                data = "", time = "", selected = "", latdms = "", londms = "",
                order = "";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
            this.longitude = longitude;
        }

        public String getNorth() {
            return north;
        }

        public void setNorth(String north) {
            this.north = north;
        }

        public String getEast() {
            return east;
        }

        public void setEast(String east) {
            this.east = east;
        }

        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public String getAltitude() {
            return altitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }

        public String getPrecision() {
            return precision;
        }

        public void setPrecision(String precision) {
            this.precision = precision;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSelected() {
            return selected;
        }

        public void setSelected(String selecionado) {
            this.selected = selecionado;
        }

        public String getLatDms() {
            return latdms;
        }

        public void setLatDms(String lat) {
            this.latdms = lat;
        }

        public String getLonDms() {
            return londms;
        }

        public void setLonDms(String lon) {
            this.londms = lon;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getOrder() {
            return this.order;
        }

    }
