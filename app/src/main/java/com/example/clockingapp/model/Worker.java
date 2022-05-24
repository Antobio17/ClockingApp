package com.example.clockingapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Worker {

    // ***************************************** CONST **************************************** //

    // ************************************** PROPERTIES ************************************** //

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "name")
    @NonNull
    private String worker;

    @ColumnInfo(name = "code_value")
    @NonNull
    private Integer codeValue;

    // *************************************** CONSTRUCT ************************************** //

    /**
     * Constructor de la clase Routine
     *
     * @param id ID de la horario.
     * @param worker Nombre del trabajador.
     * @param codeValue Valor del código PIN en entero.
     */
    public Worker(Integer id, String worker, Integer codeValue)
    {
        this.setId(id)
                .setWorker(worker)
                .setCodeValue(codeValue);
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
     * @return Worker
     */
    public Worker setId(Integer id)
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
     * @return Worker
     */
    public Worker setWorker(String worker)
    {
        this.worker = worker;

        return this;
    }

    /**
     * {@inheritDoc}
     * @return Integer
     */
    public Integer getCodeValue()
    {
        return this.codeValue;
    }

    public Worker setCodeValue(Integer codeValue)
    {
        this.codeValue = codeValue;

        return this;
    }

    // ************************************* STATIC METHODS *********************************** //

    /**
     * Método que devuelve la sentencia SQL para crear la tabla en base de datos de la clase
     * Worker.
     *
     * @return String
     */
    public static String createTable()
    {
        return "CREATE TABLE IF NOT EXISTS worker ( " +
                "id INTEGER PRIMARY KEY, " +
                "worker TEXT NOT NULL," +
                "code_value INTEGER NOT NULL," +
                ")";
    }

    /**
     * Método que devuelve la sentencia SQL para eliminar la tabla de base de datos de la clase
     * Worker.
     *
     * @return String
     */
    public static String dropTable()
    {
        return "DROP TABLE IF EXISTS worker";
    }
}
