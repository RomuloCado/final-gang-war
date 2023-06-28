using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerSpawner : MonoBehaviour {

	public GameObject[] player;

	// Use this for initialization
	void Awake () {

		int index = FindObjectOfType<GameManager>().characterIndex - 1;
		Instantiate(player[index], transform.position, transform.rotation);

	}
	
	
}
