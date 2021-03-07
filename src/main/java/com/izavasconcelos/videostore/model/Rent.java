package com.izavasconcelos.videostore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rented_movie")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rent {

  @Id
  @Column(name = "email")
  private String email;

  @Column(name = "id_movie")
  private Long movieId;
}
