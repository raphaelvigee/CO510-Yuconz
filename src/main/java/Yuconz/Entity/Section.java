package Yuconz.Entity;

import javax.persistence.*;

@Entity
@Table
public class Section
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
}
