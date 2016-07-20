package notes.sobsdes.mynoteapp;


public class Note {
    int note_id;
    String note_text;
    String note_date;
    Double longt;//долгота
    Double lat;//широта


    public Note() {//конструктор нужен для занятия места в памяти под объект

    }

    public Note(int note_id, String note_text, String note_date, Double longt, Double lat) {
        this.note_id = note_id;
        this.note_text = note_text;
        this.note_date = note_date;
        this.longt = longt;
        this.lat = lat;
    }

    public Note(String note_text, String note_date, Double longt, Double lat) {
        this.note_text = note_text;
        this.note_date = note_date;
        this.longt = longt;
        this.lat = lat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongt() {
        return longt;
    }

    public void setLongt(Double longt) {
        this.longt = longt;
    }


    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_text() {
        return note_text;
    }

    public void setNote_text(String note_text) {
        this.note_text = note_text;
    }

    public String getNote_date() {
        return note_date;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }


    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", note_text='" + note_text + '\'' +
                ", note_date='" + note_date + '\'' +
                ", longt=" + longt +
                ", lat=" + lat +
                '}';
    }
}
