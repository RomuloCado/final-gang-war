using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Attack : MonoBehaviour {

	public int damage;

    private Player playerController;
    
	// Use this for initialization
	void Start () {
        playerController = FindObjectOfType(typeof(Player)) as Player;
	}
	
	// Update is called once per frame
	void Update () {
		
	}

	private void OnTriggerEnter(Collider other)
	{
		Enemy enemy = other.GetComponent<Enemy>();
		Player player = other.GetComponent<Player>();

        if (enemy != null && !enemy.isBoss)
        {
            enemy.TookDamage(damage);
        }
        else if (enemy != null && enemy.isBoss && !playerController.onGround)
        {
            enemy.TookDamage(damage);
        }

		if (player != null)
		{
			player.TookDamage(damage);
		}
	}
}
