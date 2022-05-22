package com.example.clockingapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Schedule {

    // ***************************************** CONST **************************************** //

    // ************************************** PROPERTIES ************************************** //

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "name")
    @NonNull
    private String worker;

    @ColumnInfo(name = "checking_in")
    @NonNull
    private String checkingIn;

    @ColumnInfo(name = "checking_out")
    @NonNull
    private String checkingOut;

    @ColumnInfo(name = "day")
    @NonNull
    private String day;

    // *************************************** CONSTRUCT ************************************** //

    /**
     * Constructor de la clase Routine
     *
     * @param id ID de la horario.
     * @param worker Nombre del trabajador.
     * @param checkingIn Fecha de cuando se registra la entrada.
     * @param checkingOut Fecha de cuando se registra la salida.
     */
    public Schedule(Integer id, String worker, String checkingIn, String checkingOut, String day)
    {
        this.setId(id)
                .setWorker(worker)
                .setCheckingIn(checkingIn)
                .setCheckingOut(checkingOut)
                .setDay(day);
    }

    // *********************************** GETTERS AND SETTERS ******************************** //

    /**
     * {@inheritDoc}
     * @return Integer
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * {@inheritDoc}
     * @return Exercise
     */
    public Schedule setId(Integer id)
    {
        this.id = id;

        return this;
    }

    /**
     * {@inheritDoc}
     * @return String
     */
    @NonNull
    public String getWorker()
    {
        return this.worker;
    }

    /**
     * {@inheritDoc}
     * @return Exercise
     */
    public Schedule setWorker(String worker)
    {
        this.worker = worker;

        return this;
    }

    /**
     * {@inheritDoc}
     * @return Integer
     */
    public String getCheckingIn()
    {
        return this.checkingIn;
    }

    /**
     * {@inheritDoc}
     * @return Measurements
     */
    public Schedule setCheckingIn(String checkingIn)
    {
        this.checkingIn = checkingIn;

        return this;
    }

    /**
     * {@inheritDoc}
     * @return Integer
     */
    public String getCheckingOut()
    {
        return this.checkingOut;
    }

    /**
     * {@inheritDoc}
     * @return Measurements
     */
    public Schedule setCheckingOut(String checkingOut)
    {
        this.checkingOut = checkingOut;

        return this;
    }

    /**
     * {@inheritDoc}
     * @return Integer
     */
    public String getDay()
    {
        return this.day;
    }

    /**
     * {@inheritDoc}
     * @return Measurements
     */
    public Schedule setDay(String day)
    {
        this.day = day;

        return this;
    }

    // ************************************* STATIC METHODS *********************************** //

    /**
     * Método que devuelve la sentencia SQL para crear la tabla en base de datos de la clase
     * Schedule.
     *
     * @return String
     */
    public static String createTable()
    {
        return "CREATE TABLE IF NOT EXISTS schedule ( " +
                "id INTEGER PRIMARY KEY, " +
                "worker TEXT NOT NULL," +
                "checkingIn TEXT NOT NULL," +
                "checkingOut TEXT NOT NULL," +
                "day TEXT NOT NULL," +
                ")";
    }

    /**
     * Método que devuelve la sentencia SQL para eliminar la tabla de base de datos de la clase
     * Schedule.
     *
     * @return String
     */
    public static String dropTable()
    {
        return "DROP TABLE IF EXISTS schedule";
    }
}
