package Yuconz.Entity;

import javax.persistence.*;

@Entity
@Table
public class Department
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;


}
